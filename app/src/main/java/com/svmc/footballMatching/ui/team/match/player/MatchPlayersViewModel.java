package com.svmc.footballMatching.ui.team.match.player;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.callBackInterface.QueryPlayersCallBack;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.data.repository.UserRepository;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.data.session.Session;

import java.util.List;

public class MatchPlayersViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private MatchPlayersRecyclerViewAdapter adapter = new MatchPlayersRecyclerViewAdapter();
    private MutableLiveData<LoadDataState> playersLoadStateLiveData = new MutableLiveData<>(LoadDataState.INIT);
    public String playersLoadMessage;

    public MatchPlayersViewModel() {
        queryAllPlayers(Session.getInstance().getUserLiveData().getValue().getId(),
                CurrentTeam.getInstance().getMyTeamLiveData().getValue().getId());
    }

    private void queryAllPlayers(String myId, String myTeamId) {
        playersLoadStateLiveData.setValue(LoadDataState.LOADING);
        userRepository.queryAllPlayers(myId, myTeamId, new QueryPlayersCallBack() {
            @Override
            public void onSuccess(List<User> players) {
                adapter.setPlayers(players);
                adapter.notifyDataSetChanged();
                playersLoadStateLiveData.setValue(LoadDataState.LOADED);
            }

            @Override
            public void onFailure(String message) {
                playersLoadMessage = message;
                playersLoadStateLiveData.setValue(LoadDataState.ERROR);
                System.out.println(message);
            }
        });
    }

    public MatchPlayersRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public LiveData<LoadDataState> getPlayersLoadStateLiveData() {
        return playersLoadStateLiveData;
    }
}
