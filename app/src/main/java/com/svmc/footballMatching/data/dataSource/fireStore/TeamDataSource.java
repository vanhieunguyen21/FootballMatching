package com.svmc.footballMatching.data.dataSource.fireStore;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.svmc.footballMatching.callBackInterface.GetTeamCallBack;
import com.svmc.footballMatching.callBackInterface.UpdateProfileCallBack;
import com.svmc.footballMatching.data.model.Location;
import com.svmc.footballMatching.data.model.Photo;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.session.CurrentTeam;

import static com.svmc.footballMatching.util.MapUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamDataSource {
    private final String TAG = "TeamDataSource";
    private static TeamDataSource instance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFunctions functions = FirebaseFunctions.getInstance();

    private TeamDataSource() {
    }

    public static TeamDataSource getInstance() {
        if (instance == null) {
            instance = new TeamDataSource();
        }
        return instance;
    }

    public void getTeam(String teamId, final GetTeamCallBack callBack) {
        functions.getHttpsCallable("getTeam").call(teamId).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Map<String, Object> result = (Map<String, Object>) httpsCallableResult.getData();
                if (result != null) {

                } else {
                    callBack.onFailure("No team with id found");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private Team teamRecordToTeam(Map<String, Object> teamRecord) {
        Team team = new Team();
        team.setId(valueToStringOrNull(teamRecord, "id"));
        team.setName(valueToStringOrNull(teamRecord, "name"));
        team.setAvatar(valueToStringOrNull(teamRecord, "avatar"));
        // set photos
        List<Photo> photos = new ArrayList<>();
        ArrayList<Map<String, Object>> photoMaps = (ArrayList<Map<String, Object>>) teamRecord.get("photos");
        for (Map<String, Object> photoMap : photoMaps) {
            Photo photo = mapToPhoto(photoMap);
            photos.add(photo);
        }
        team.setPhotos(photos);
        team.setContactTelephones((ArrayList<String>) teamRecord.get("contactTelephones"));
        team.setContactEmails((ArrayList<String>) teamRecord.get("contactEmails"));
        // set location
        Map<String, Object> locationMap = (Map<String, Object>) teamRecord.get("location");
        if (locationMap != null) {
            Double longitude = valueToDoubleOrNull(locationMap, "longitude");
            Double latitude = valueToDoubleOrNull(locationMap, "latitude");
            String geoHash = valueToStringOrNull(locationMap, "geoHash");
            if (longitude != null && latitude != null)
                team.setLocation(new Location(longitude, latitude, geoHash));
        }

        team.setIntroduction(valueToStringOrNull(teamRecord, "introduction"));
        team.setStatus(valueToStringOrNull(teamRecord, "status"));
        team.setMainGroup(valueToIntOrNegativeOne(teamRecord, "mainGroup"));
        team.setGroups((ArrayList<Integer>) teamRecord.get("groups"));
        // set team members
        List<Team.TeamMember> teamMembers = new ArrayList<>();
        ArrayList<Map<String, Object>> teamMemberMap = (ArrayList<Map<String, Object>>) teamRecord.get("teamMembers");
        if (teamMemberMap != null) {

        }
        return null;
    }


    public void updateTeamProfile(final String teamId, Map<String, Object> updateIntroduction, final UpdateProfileCallBack callBack) {
        db.collection("teams").document(teamId).update(updateIntroduction).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getTeam(teamId, new GetTeamCallBack() {
                    @Override
                    public void onSuccess(Team team) {
                        CurrentTeam.getInstance().setTeam(team);
                        callBack.onSuccess();
                    }

                    @Override
                    public void onFailure(String message) {
                        callBack.onFailure(message);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }
}
