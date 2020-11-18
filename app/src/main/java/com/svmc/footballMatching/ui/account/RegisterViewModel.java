package com.svmc.footballMatching.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.callBackInterface.RegisterCallBack;
import com.svmc.footballMatching.data.enumeration.Result;
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

    private MutableLiveData<Result> registerResultLiveData = new MutableLiveData<>();
    public String registerMessage;

    public void register(String email, String password, String fullName) {
        userRepository.register(email, password, fullName, new RegisterCallBack() {
            @Override
            public void onSuccess() {
                registerResultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                registerMessage = message;
                registerResultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public LiveData<Result> getRegisterResultLiveData(){
        return registerResultLiveData;
    }
}
