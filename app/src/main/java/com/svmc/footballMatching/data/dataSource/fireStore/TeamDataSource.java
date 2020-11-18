package com.svmc.footballMatching.data.dataSource.fireStore;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.svmc.footballMatching.callBackInterface.CreateTeamCallBack;
import com.svmc.footballMatching.callBackInterface.GetTeamCallBack;
import com.svmc.footballMatching.callBackInterface.LikePlayerCallBack;
import com.svmc.footballMatching.callBackInterface.LikeTeamCallBack;
import com.svmc.footballMatching.callBackInterface.QueryTeamsCallBack;
import com.svmc.footballMatching.callBackInterface.UpdateProfileCallBack;
import com.svmc.footballMatching.data.model.Location;
import com.svmc.footballMatching.data.model.Photo;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.data.session.Session;

import static com.svmc.footballMatching.util.MapUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
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
                Map<String, Object> data = (Map<String, Object>) httpsCallableResult.getData();
                if (data != null) {
                    Team team = mapToTeam(data);
                    callBack.onSuccess(team);
                } else {
                    callBack.onFailure("No team with id found");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
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

    public void createTeam(String teamName, final CreateTeamCallBack callBack) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", teamName);
        data.put("leader", Session.getInstance().getUserLiveData().getValue().getId());
        functions.getHttpsCallable("createTeam").call(data).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Map<String, Object> result = (Map<String, Object>) httpsCallableResult.getData();
                if (result != null && (int) result.get("result") == 1) {
                    callBack.onSuccess();
                } else if (result != null && (int) result.get("result") == 1) {
                    String message = (String) result.get("errorMessage");
                    callBack.onFailure(message);
                } else {
                    callBack.onFailure("Unknown error, try again later!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public void updateJoinedTeams(String uid) {
        functions.getHttpsCallable("getJoinedTeams").call(uid).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                List<User.JoinedTeam> joinedTeams = new ArrayList<>();
                ArrayList<Map<String, Object>> joinedTeamMaps = (ArrayList<Map<String, Object>>) httpsCallableResult.getData();
                if (joinedTeamMaps != null) {
                    for (Map<String, Object> joinedTeamMap : joinedTeamMaps) {
                        User.JoinedTeam joinedTeam = mapToJoinedTeam(joinedTeamMap);
                        if (joinedTeam != null) joinedTeams.add(joinedTeam);
                    }
                }
                User user = Session.getInstance().getUserLiveData().getValue();
                user.setJoinedTeams(joinedTeams);
                System.out.println("eh" + joinedTeams.get(0).getTeam().getName());
                Session.getInstance().setUser(user);
            }
        });
    }

    public void likeTeam(String sourceTeamId, String destinationTeamId, final LikeTeamCallBack callBack) {
        Map<String, Object> data = new HashMap<>();
        data.put("sourceTeamId", sourceTeamId);
        data.put("destinationTeamId", destinationTeamId);
        functions.getHttpsCallable("match-teamLikeTeam").call(data).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Map<String, Object> result = (Map<String, Object>) httpsCallableResult.getData();
                if (result != null) {
                    if ((int) result.get("result") == 1) {
                        callBack.onSuccess();
                    } else {
                        String message = (String) result.get("errorMessage");
                        callBack.onFailure(message);
                    }
                } else {
                    callBack.onFailure("Unknown error");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public void queryAllTeams(String myTeamId, final QueryTeamsCallBack callBack) {
        functions.getHttpsCallable("queryTeams-queryAllTeams").call(myTeamId).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Map<String, Object> data = (Map<String, Object>) httpsCallableResult.getData();
                ArrayList<Map<String, Object>> teamMaps = (ArrayList<Map<String, Object>>) data.get("result");
                List<Team> teams = new ArrayList<>();
                if (teamMaps != null) {
                    for (Map<String, Object> teamMap : teamMaps) {
                        Team team = mapToTeam(teamMap);
                        if (team != null) teams.add(team);
                    }
                }
                callBack.onSuccess(teams);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public ListenerRegistration listenToTeamLikedByTeams(String teamId, EventListener<QuerySnapshot> eventListener) {
        return db.collection("teams")
                .document(teamId)
                .collection("likedByTeams")
                .addSnapshotListener(eventListener);
    }

    public ListenerRegistration listenToTeamLikedByPlayers(String teamId, EventListener<QuerySnapshot> eventListener) {
        return db.collection("teams")
                .document(teamId)
                .collection("likedByPlayers")
                .addSnapshotListener(eventListener);
    }

    public void updateLastUpdateNotification(String teamId) {
        Map<String, Object> data = new HashMap<>();
        data.put("lastUpdateNotification", Timestamp.now());
        db.collection("teams").document(teamId).update(data);
    }

    public void likePlayer(String sourceTeamId, String playerId, final LikePlayerCallBack callBack) {
        Map<String, Object> data = new HashMap<>();
        data.put("sourceTeamId", sourceTeamId);
        data.put("playerId", playerId);
        functions.getHttpsCallable("match-teamLikePlayer").call(data).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Map<String, Object> result = (Map<String, Object>) httpsCallableResult.getData();
                if (result != null) {
                    if ((int) result.get("result") == 1) {
                        callBack.onSuccess();
                    } else {
                        String message = (String) result.get("errorMessage");
                        callBack.onFailure(message);
                    }
                } else {
                    callBack.onFailure("Unknown error");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public void queryNotJoinedTeams(String uid, final QueryTeamsCallBack callBack) {
        functions.getHttpsCallable("queryTeams-queryNotJoinedTeams").call(uid).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Map<String, Object> data = (Map<String, Object>) httpsCallableResult.getData();
                ArrayList<Map<String, Object>> teamMaps = (ArrayList<Map<String, Object>>) data.get("result");
                List<Team> teams = new ArrayList<>();
                if (teamMaps != null) {
                    for (Map<String, Object> teamMap : teamMaps) {
                        Team team = mapToTeam(teamMap);
                        if (team != null) teams.add(team);
                    }
                }
                callBack.onSuccess(teams);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }


}
