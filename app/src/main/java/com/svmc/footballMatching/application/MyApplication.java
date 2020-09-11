package com.svmc.footballMatching.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.svmc.footballMatching.data.repository.UserRepository;
import com.svmc.footballMatching.data.session.Session;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Session.initSession(this);
        UserRepository userRepository = UserRepository.getInstance();
        if (userRepository.isLoggedIn()) {
            // Get user information from shared preference and set it to session
            SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        }
    }
}
