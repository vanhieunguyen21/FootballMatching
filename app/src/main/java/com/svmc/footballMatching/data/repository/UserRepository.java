package com.svmc.footballMatching.data.repository;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.svmc.footballMatching.callBackInterface.GetUserCallBack;
import com.svmc.footballMatching.callBackInterface.LikeTeamCallBack;
import com.svmc.footballMatching.callBackInterface.LoginCallBack;
import com.svmc.footballMatching.callBackInterface.QueryPlayersCallBack;
import com.svmc.footballMatching.callBackInterface.RegisterCallBack;
import com.svmc.footballMatching.callBackInterface.UpdateProfileCallBack;
import com.svmc.footballMatching.data.dataSource.fireStore.UserDataSource;
import com.svmc.footballMatching.ui.account.LoginViewModel;

import java.util.Map;

public class UserRepository {
    private static UserRepository instance;
    private UserDataSource userDataSource = UserDataSource.getInstance();

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void login(String email, String password, int loginRequestCode, LoginCallBack callBack) {
        userDataSource.login(email, password, loginRequestCode, callBack);
    }

    public void register(String email, String password, String fullName, RegisterCallBack callBack) {
        userDataSource.register(email, password, fullName, callBack);
    }

    public void logout() {
        userDataSource.logout();
    }

    public boolean isLoggedIn() {
        return userDataSource.isLoggedIn();
    }

    public void updateProfile(Map<String, Object> updateBasicInformation, UpdateProfileCallBack callBack) {
        userDataSource.updateProfile(updateBasicInformation, callBack);
    }

    public void loginWithCurrentAuthAccount(int loginRequestCode, LoginCallBack loginCallBack) {
        userDataSource.loginWithCurrentAuthAccount(loginRequestCode, loginCallBack);
    }

    public void likeTeam(String playerId, String destinationTeamId, LikeTeamCallBack callBack) {
        userDataSource.likeTeam(playerId, destinationTeamId, callBack);
    }

    public void queryAllPlayers(String myId, String myTeamId, QueryPlayersCallBack callBack) {
        userDataSource.queryAllPlayers(myId, myTeamId, callBack);
    }

    public void getUser(String uid, GetUserCallBack callBack){
        userDataSource.getUser(uid, callBack);
    }

    public void updateLastUpdateNotification(String uid) {
        userDataSource.updateLastUpdateNotification(uid);
    }

    public ListenerRegistration listenToPlayerLikedByTeams(String uid, EventListener<QuerySnapshot> eventListener) {
        return userDataSource.listenToPlayerLikedByTeams(uid, eventListener);
    }
}
