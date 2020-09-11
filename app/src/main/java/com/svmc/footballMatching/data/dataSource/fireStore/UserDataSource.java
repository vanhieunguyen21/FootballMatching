package com.svmc.footballMatching.data.dataSource.fireStore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.svmc.footballMatching.callBackInterface.LoginCallBack;
import com.svmc.footballMatching.callBackInterface.RegisterCallBack;
import com.svmc.footballMatching.callBackInterface.UpdateProfileCallBack;
import com.svmc.footballMatching.data.model.Location;
import com.svmc.footballMatching.data.model.Photo;
import com.svmc.footballMatching.data.model.user.Player;
import com.svmc.footballMatching.data.model.user.Referee;
import com.svmc.footballMatching.data.model.user.User;
import com.svmc.footballMatching.data.session.Session;

import static com.svmc.footballMatching.util.MapUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataSource {
    private final String TAG = "UserDataSource";
    private static UserDataSource instance;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFunctions functions = FirebaseFunctions.getInstance();

    private UserDataSource() {
    }

    public static UserDataSource getInstance() {
        if (instance == null) {
            instance = new UserDataSource();
        }
        return instance;
    }

    public void register(String email, String password, String fullName, String userType, final RegisterCallBack callBack) {
        Log.d(TAG, "register");
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("fullName", fullName);
        data.put("userType", userType);
        functions.getHttpsCallable("createUser")
                .call(data)
                .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                    @Override
                    public void onSuccess(HttpsCallableResult httpsCallableResult) {
                        Map<String, Object> result = (Map<String, Object>) httpsCallableResult.getData();
                        if (result != null && (int) result.get("result") == 1) {
                            callBack.onSuccess();
                            Log.d(TAG, "Register successfully");
                        } else if (result != null && (int) result.get("result") == 0) {
                            String message = (String) result.get("errorMessage");
                            Log.d(TAG, "Register failed, message: " + message);
                            callBack.onFailure("Register failed, message: " + message);
                        } else {
                            callBack.onFailure("Unknown error, try again later!");
                            Log.d(TAG, "Create account failed, unknown error");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Register failed: ", e);
                        callBack.onFailure("Register failed " + e.getMessage());
                    }
                });
    }

    public void login(String email, String password, final int loginRequestCode, final LoginCallBack callBack) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(final AuthResult authResult) {
                loginWithCurrentAuthAccount(loginRequestCode, callBack);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Login failed, failed to login", e);
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public void loginWithCurrentAuthAccount(int loginRequestCode, LoginCallBack loginCallBack) {
        getUserInformation(auth.getCurrentUser().getUid(), loginRequestCode, loginCallBack);
    }

    private void getUserInformation(String uid, final int loginRequestCode, final LoginCallBack callBack) {
        functions.getHttpsCallable("getUser").call(uid).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Object data = httpsCallableResult.getData();
                if (data != null) {
                    Map<String, Object> userRecord = (HashMap<String, Object>) data;
                    User user = userRecordToUser(userRecord);
                    if (user != null) {
                        callBack.onSuccess(user, loginRequestCode);
                    } else {
                        callBack.onFailure("Unknown user type");
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed to get user information, message: ", e);
                logout();
                callBack.onFailure("Failed to get user information, message: " + e.getMessage());
            }
        });
    }

    public void logout() {
        auth.signOut();
    }

    @SuppressWarnings("unchecked")
    private User userRecordToUser(Map<String, Object> userRecord) {
        String userType = valueToStringOrNull(userRecord, "userType");
        User user;
        switch (userType) {
            case "Player":
                user = new Player();
                user = setPlayerInformation(user, userRecord);
                break;
            case "Referee":
                user = new Referee();
                user = setRefereeInformation(user, userRecord);
                break;
            case "Other":
                user = new User();
                break;
            default:
                return null;
        }
        // Set id
        user.setId(valueToStringOrNull(userRecord, "id"));
        // Set email
        user.setEmail(valueToStringOrNull(userRecord, "email"));
        // Set full name
        user.setFullName(valueToStringOrNull(userRecord, "fullName"));
        // Set photos
        List<Photo> photos = new ArrayList<>();
        ArrayList<Map<String, Object>> photoMaps = (ArrayList<Map<String, Object>>) userRecord.get("photos");
        for (Map<String, Object> photoMap : photoMaps) {
            Photo photo = mapToPhoto(photoMap);
            photos.add(photo);
        }
        user.setPhotos(photos);
        //Set address
        user.setAddress(valueToStringOrNull(userRecord, "address"));
        // Set birthday
        Map<String, Object> birthdayMap = (Map<String, Object>) userRecord.get("birthday");
        if (birthdayMap != null) user.setBirthday(mapToTimestamp(birthdayMap));
        // Set phone
        user.setPhone(valueToStringOrNull(userRecord, "phone"));
        // Set joined timestamp
        user.setJoinedTimestamp(mapToTimestamp((Map<String, Object>) userRecord.get("joinedTimestamp")));
        // Set active status
        user.setAccountActive(valueToBooleanOrFalse(userRecord, "accountActive"));

        return user;
    }

    @SuppressWarnings("unchecked")
    private User setPlayerInformation(User user, Map<String, Object> userRecord) {
        Player player = (Player) user;
        // Set height
        player.setHeight(valueToIntOrZero(userRecord, "height"));
        // Set weight
        player.setWeight(valueToIntOrZero(userRecord, "weight"));
        // Set introduction
        player.setIntroduction(valueToStringOrNull(userRecord, "introduction"));
        // Set preferred positions
        player.setPreferredPositions((Map<String, Object>) userRecord.get("preferredPositions"));
        // Set schedule
        // TODO
        // Set location
        Map<String, Object> locationMap = (Map<String, Object>) userRecord.get("location");
        if (locationMap != null) {
            Double longitude = valueToDoubleOrNull(locationMap, "longitude");
            Double latitude = valueToDoubleOrNull(locationMap, "latitude");
            String geoHash = valueToStringOrNull(locationMap, "geoHash");
            if (longitude != null && latitude != null)
                player.setLocation(new Location(longitude, latitude, geoHash));
        }
        // Set joined teams
        List<Player.JoinedTeam> joinedTeams = new ArrayList<>();
        ArrayList<Map<String, Object>> joinedTeamMaps = (ArrayList<Map<String, Object>>) userRecord.get("joinedTeams");
        if (joinedTeamMaps != null) {
            for (Map<String, Object> joinedTeamMap : joinedTeamMaps) {
                Player.JoinedTeam joinedTeam = mapToJoinedTeam(joinedTeamMap);
                joinedTeams.add(joinedTeam);
            }
        }
        player.setJoinedTeams(joinedTeams);
        // Set liked teams
        List<Player.LikedTeam> likedTeams = new ArrayList<>();
        ArrayList<Map<String, Object>> likedTeamMaps = (ArrayList<Map<String, Object>>) userRecord.get("likedTeams");
        for (Map<String, Object> likedTeamMap : likedTeamMaps) {
            Player.LikedTeam likedTeam = mapToLikedTeam(likedTeamMap);
            likedTeams.add(likedTeam);
        }
        player.setLikedTeams(likedTeams);
        // Set liked by teams
        List<Player.LikedTeam> likedByTeams = new ArrayList<>();
        ArrayList<Map<String, Object>> likedByTeamMaps = (ArrayList<Map<String, Object>>) userRecord.get("likedByTeams");
        for (Map<String, Object> likedByTeamMap : likedByTeamMaps) {
            Player.LikedTeam likedTeam = mapToLikedTeam(likedByTeamMap);
            likedTeams.add(likedTeam);
        }
        player.setLikedByTeams(likedTeams);
        return player;
    }

    private User setRefereeInformation(User user, Map<String, Object> userRecord) {
        Referee referee = (Referee) user;
        // Set schedule
        // TODO
        return referee;
    }

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public void updateProfile(Map<String, Object> updateBasicInformation, final UpdateProfileCallBack callBack) {
        String uid = Session.getInstance().getUserLiveData().getValue().getId();
        db.collection("users").document(uid).update(updateBasicInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loginWithCurrentAuthAccount(0, new LoginCallBack() {
                    @Override
                    public void onSuccess(User user, int loginRequestCode) {
                        Session.getInstance().setUser(user);
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
