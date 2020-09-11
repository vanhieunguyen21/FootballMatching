package com.svmc.footballMatching.ui.personalProfile;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.model.user.Player;
import com.svmc.footballMatching.data.model.user.User;
import com.svmc.footballMatching.data.session.Session;

public class PlayerPersonalProfileViewModel extends ViewModel {
    public LiveData<Player> playerLiveData = Transformations.map(Session.getInstance().getUserLiveData(), new Function<User, Player>() {
        @Override
        public Player apply(User input) {
            return (Player) input;
        }
    });


}
