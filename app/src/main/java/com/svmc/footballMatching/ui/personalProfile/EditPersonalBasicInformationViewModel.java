package com.svmc.footballMatching.ui.personalProfile;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.callBackInterface.UpdateProfileCallBack;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.user.User;
import com.svmc.footballMatching.data.repository.UserRepository;
import com.svmc.footballMatching.data.session.Session;

import java.util.Map;

public class EditPersonalBasicInformationViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();

    public LiveData<User> userLiveData = Transformations.map(Session.getInstance().getUserLiveData(), new Function<User, User>() {
        @Override
        public User apply(User input) {
            return input;
        }
    });

    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void updateProfile(Map<String, Object> updateBasicInformation) {
        userRepository.updateProfile(updateBasicInformation, new UpdateProfileCallBack() {
            @Override
            public void onSuccess() {
                resultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }
}
