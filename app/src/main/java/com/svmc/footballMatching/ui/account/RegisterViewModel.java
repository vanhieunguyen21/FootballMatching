package com.svmc.footballMatching.ui.account;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.repository.UserRepository;

public class RegisterViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    /**
     * Live data for validating input form
     * 0: Default state
     * null: No error state
     * others: String resource id for current field error
     **/
    private MutableLiveData<Integer> emailStateLiveData = new MutableLiveData<>(0);
    private MutableLiveData<Boolean> emailCheckingStateLiveData = new MutableLiveData<>(false);
    private MutableLiveData<Integer> passwordStateLiveData = new MutableLiveData<>(0);
    private MutableLiveData<Integer> confirmPasswordStateLiveData = new MutableLiveData<>(0);
    private MutableLiveData<Integer> fullNameStateLiveData = new MutableLiveData<>(0);
}
