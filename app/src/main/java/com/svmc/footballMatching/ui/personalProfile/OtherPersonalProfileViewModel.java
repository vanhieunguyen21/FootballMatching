package com.svmc.footballMatching.ui.personalProfile;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.model.user.User;
import com.svmc.footballMatching.data.session.Session;

public class OtherPersonalProfileViewModel extends ViewModel {
    public LiveData<User> userLiveData = Transformations.map(Session.getInstance().getUserLiveData(), new Function<User, User>() {
        @Override
        public User apply(User input) {
            return input;
        }
    });
}
