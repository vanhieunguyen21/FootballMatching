package com.svmc.footballMatching.data.dataSource.sharedPreference;

import android.content.SharedPreferences;

import com.svmc.footballMatching.data.model.User;

public class SharedPreferencesUtils {
    public static User sharedPreferencesToUser(SharedPreferences sharedPreferences) {
        User user = new User();
        return user;
    }

    public static void userToSharedPreferences(User user, SharedPreferences sharedPreferences) {

    }
}
