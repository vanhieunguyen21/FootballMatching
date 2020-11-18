package com.svmc.footballMatching.data.dataSource.fireStore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.svmc.footballMatching.callBackInterface.GetUserCallBack;
import com.svmc.footballMatching.callBackInterface.LikeTeamCallBack;
import com.svmc.footballMatching.callBackInterface.LoginCallBack;
import com.svmc.footballMatching.callBackInterface.QueryPlayersCallBack;
import com.svmc.footballMatching.callBackInterface.RegisterCallBack;
import com.svmc.footballMatching.callBackInterface.UpdateProfileCallBack;
import com.svmc.footballMatching.data.model.Location;
import com.svmc.footballMatching.data.model.Photo;
import com.svmc.footballMatching.data.model.User;
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

    public void register(String email, String password, String fullName, final RegisterCallBack callBack) {
        Log.d(TAG, "register");
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("fullName", fullName);
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
                    User user = mapToUser(userRecord);
                    if (user != null) {
                        callBack.onSuccess(user, loginRequestCode);
                    } else {
                        callBack.onFailure("Unknown error");
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

    public void getUser(String uid, final GetUserCallBack callBack) {
        functions.getHttpsCallable("getUser").call(uid).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Object data = httpsCallableResult.getData();
                if (data != null) {
                    Map<String, Object> userRecord = (HashMap<String, Object>) data;
                    User user = mapToUser(userRecord);
                    if (user != null) {
                        callBack.onSuccess(user);
                    } else {
                        callBack.onFailure("Unknown error");
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public void logout() {
        auth.signOut();
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

    public void likeTeam(String playerId, String destinationTeamId, final LikeTeamCallBack callBack) {
        Map<String, Object> data = new HashMap<>();
        data.put("playerId", playerId);
        data.put("destinationTeamId", destinationTeamId);
        functions.getHttpsCallable("match-playerLikeTeam").call(data).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
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

    public void queryAllPlayers(String myId, String myTeamId, final QueryPlayersCallBack callBack) {
        Map<String, Object> data = new HashMap<>();
        data.put("myId", myId);
        data.put("myTeamId", myTeamId);
        functions.getHttpsCallable("queryAllPlayers").call(data).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Map<String, Object> data = (Map<String, Object>) httpsCallableResult.getData();
                ArrayList<Map<String, Object>> playerMaps = (ArrayList<Map<String, Object>>) data.get("result");
                List<User> players = new ArrayList<>();
                if (playerMaps != null) {
                    for (Map<String, Object> playerMap : playerMaps) {
                        User player = mapToUser(playerMap);
                        if (player != null) players.add(player);
                    }
                }
                callBack.onSuccess(players);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onFailure(e.getMessage());
            }
        });
    }

    public void updateLastUpdateNotification(String uid) {
        Map<String, Object> data = new HashMap<>();
        data.put("lastUpdateNotification", Timestamp.now());
        db.collection("teams").document(uid).update(data);
    }

    public ListenerRegistration listenToPlayerLikedByTeams(String uid, EventListener<QuerySnapshot> eventListener) {
        return db.collection("users")
                .document(uid)
                .collection("likedByTeams")
                .addSnapshotListener(eventListener);
    }
}
