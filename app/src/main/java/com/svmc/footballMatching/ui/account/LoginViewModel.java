package com.svmc.footballMatching.ui.account;

import android.text.Editable;
import android.util.Patterns;

import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.R;
import com.svmc.footballMatching.callBackInterface.LoginCallBack;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.data.repository.UserRepository;
import com.svmc.footballMatching.data.session.Session;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    // Login request code to match login request
    private int loginRequestCode = 0;

    /**
     * Live data for validating input form
     * 0: Default state
     * null: No error state
     * others: String resource id for current field error
     **/
    private MutableLiveData<Integer> emailStateLiveData = new MutableLiveData<>(0);
    private MutableLiveData<Integer> passwordStateLiveData = new MutableLiveData<>(0);

    private MutableLiveData<Boolean> dataValidLiveData = new MutableLiveData<>(false);

    private MutableLiveData<Result> loginResultLiveData = new MutableLiveData<>(null);
    private String loginMessage;

    LiveData<Integer> getEmailStateLiveData() {
        return emailStateLiveData;
    }

    LiveData<Integer> getPasswordStateLiveData() {
        return passwordStateLiveData;
    }

    LiveData<Result> getLoginResultLiveData() {
        return loginResultLiveData;
    }

    public LiveData<Boolean> getDataValidLiveData() {
        return dataValidLiveData;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public boolean isLoggedIn() {
        return userRepository.isLoggedIn();
    }

    public void login(String email, String password) {
        userRepository.login(email, password, loginRequestCode, new LoginCallBack() {
            @Override
            public void onSuccess(User user, int loginRequestCode) {
                if (LoginViewModel.this.loginRequestCode == loginRequestCode) {
                    LoginViewModel.this.loginRequestCode++;
                    Session.getInstance().setUser(user);
                    loginResultLiveData.setValue(Result.SUCCESS);
                } else {
                    userRepository.logout();
                }
            }

            @Override
            public void onFailure(String message) {
                loginMessage = message;
                loginResultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public void loginWithCurrentAuthAccount() {
        userRepository.loginWithCurrentAuthAccount(loginRequestCode, new LoginCallBack() {

            @Override
            public void onSuccess(User user, int loginRequestCode) {
                if (LoginViewModel.this.loginRequestCode == loginRequestCode) {
                    LoginViewModel.this.loginRequestCode++;
                    Session.getInstance().setUser(user);
                    loginResultLiveData.setValue(Result.SUCCESS);
                } else {
                    userRepository.logout();
                }
            }

            @Override
            public void onFailure(String message) {
                loginMessage = message;
                loginResultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public void cancelLoginRequest() {
        loginRequestCode++;
        userRepository.logout();
    }

    public TextViewBindingAdapter.AfterTextChanged emailChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            String email = s.toString();
            if (email.isEmpty()) {
                emailStateLiveData.setValue(0);
            } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailStateLiveData.setValue(null);
            } else {
                emailStateLiveData.setValue(R.string.invalid_email);
            }

            dataValidLiveData.setValue(isDataValid());
        }
    };

    public TextViewBindingAdapter.AfterTextChanged passwordChanged = new TextViewBindingAdapter.AfterTextChanged() {
        @Override
        public void afterTextChanged(Editable s) {
            String password = s.toString();
            if (password.isEmpty()) {
                passwordStateLiveData.setValue(0);
            } else if (password.length() > 5) {
                passwordStateLiveData.setValue(null);
            } else {
                passwordStateLiveData.setValue(R.string.invalid_password_length);
            }

            dataValidLiveData.setValue(isDataValid());
        }
    };

    private boolean isDataValid() {
        return (emailStateLiveData.getValue() == null && passwordStateLiveData.getValue() == null);
    }
}
