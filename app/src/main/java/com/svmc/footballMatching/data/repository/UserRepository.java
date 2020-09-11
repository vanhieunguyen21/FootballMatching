package com.svmc.footballMatching.data.repository;

import com.svmc.footballMatching.callBackInterface.LoginCallBack;
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

    public void register(String email, String password, String fullName, String userType, RegisterCallBack callBack) {
        userDataSource.register(email, password, fullName, userType, callBack);
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
}
