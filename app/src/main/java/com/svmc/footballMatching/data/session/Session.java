package com.svmc.footballMatching.data.session;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.svmc.footballMatching.data.dataSource.sharedPreference.SharedPreferencesUtils;
import com.svmc.footballMatching.data.model.user.User;
import com.svmc.footballMatching.data.repository.UserRepository;

public class Session {
    private static Context applicationContext;
    private static Session instance;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private UserRepository userRepository = UserRepository.getInstance();

    private Session() {
    }

    public static void initSession(Application application) {
        Session.applicationContext = application.getApplicationContext();
        if (instance == null)
            instance = new Session();
    }

    public static Session getInstance() {
        if (instance != null)
            return instance;
        throw new IllegalArgumentException("Session is not initiated");
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void setUser(User user) {
        userLiveData.setValue(user);
        // Write user to shared preferences
        SharedPreferencesUtils.userToSharedPreferences(user, applicationContext.getSharedPreferences("userData", Context.MODE_PRIVATE));
    }

    public void logout() {
        userRepository.logout();
        setUser(null);
    }
}
