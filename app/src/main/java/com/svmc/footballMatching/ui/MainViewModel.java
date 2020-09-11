package com.svmc.footballMatching.ui;

import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.repository.UserRepository;

public class MainViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    public boolean isLoggedIn() {
        return userRepository.isLoggedIn();
    }

    public void logout() {
        userRepository.logout();
    }
}
