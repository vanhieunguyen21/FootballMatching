package com.svmc.footballMatching.ui.team.match.player;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.callBackInterface.GetUserCallBack;
import com.svmc.footballMatching.callBackInterface.LikePlayerCallBack;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.data.repository.TeamRepository;
import com.svmc.footballMatching.data.repository.UserRepository;
import com.svmc.footballMatching.data.session.CurrentTeam;

public class PlayerProfileViewModel extends ViewModel {
    private MutableLiveData<User> playerLiveData;
    private UserRepository userRepository = UserRepository.getInstance();
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MutableLiveData<LoadDataState> playerLoadStateLiveData = new MutableLiveData<>(LoadDataState.INIT);
    public String playerLoadMessage;
    private MutableLiveData<Result> likePlayerResultLiveData = new MutableLiveData<>();
    public String likePlayerResultMessage = null;
    private MutableLiveData<Result> invitePlayerResultLiveData = new MutableLiveData<>();
    public String invitePlayerResultMessage;

    public PlayerProfileViewModel(final User player) {
        playerLiveData = new MutableLiveData<>();
        loadPlayer(player);
    }

    private void loadPlayer(final User player) {
        playerLoadStateLiveData.setValue(LoadDataState.LOADING);
        userRepository.getUser(player.getId(), new GetUserCallBack() {
            @Override
            public void onSuccess(User user) {
                playerLiveData.setValue(player);
                playerLoadStateLiveData.setValue(LoadDataState.LOADED);
            }

            @Override
            public void onFailure(String message) {
                playerLoadMessage = message;
                playerLoadStateLiveData.setValue(LoadDataState.ERROR);
            }
        });
    }

    public void likePlayer() {
        teamRepository.likePlayer(CurrentTeam.getInstance().getMyTeamLiveData().getValue().getId(), playerLiveData.getValue().getId(), new LikePlayerCallBack() {
            @Override
            public void onSuccess() {
                likePlayerResultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                likePlayerResultMessage = message;
                likePlayerResultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public MutableLiveData<User> getPlayerLiveData() {
        return playerLiveData;
    }

    public MutableLiveData<LoadDataState> getPlayerLoadStateLiveData() {
        return playerLoadStateLiveData;
    }

    public MutableLiveData<Result> getLikePlayerResultLiveData() {
        return likePlayerResultLiveData;
    }

    public MutableLiveData<Result> getInvitePlayerResultLiveData() {
        return invitePlayerResultLiveData;
    }

    public static class PlayerProfileViewModelFactory implements ViewModelProvider.Factory {
        private User player;

        public PlayerProfileViewModelFactory(User player) {
            this.player = player;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(PlayerProfileViewModel.class)) {
                return (T) new PlayerProfileViewModel(player);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
