package com.svmc.footballMatching.ui.account.match;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.callBackInterface.QueryTeamsCallBack;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.repository.TeamRepository;
import com.svmc.footballMatching.data.session.Session;

import java.util.List;

public class PlayerFindTeamsViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private TeamsRecyclerViewAdapter adapter = new TeamsRecyclerViewAdapter();
    private MutableLiveData<LoadDataState> teamsLoadStateLiveData = new MutableLiveData<>(LoadDataState.INIT);
    public String teamsLoadMessage;

    public PlayerFindTeamsViewModel() {
        queryNotJoinedTeams(Session.getInstance().getUserLiveData().getValue().getId());
    }

    public TeamsRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public LiveData<LoadDataState> getTeamsLoadStateLiveData() {
        return teamsLoadStateLiveData;
    }

    public void queryNotJoinedTeams(String uid) {
        teamsLoadStateLiveData.setValue(LoadDataState.LOADING);
        teamRepository.queryNotJoinedTeams(uid, new QueryTeamsCallBack() {
            @Override
            public void onSuccess(List<Team> teams) {
                teamsLoadStateLiveData.setValue(LoadDataState.LOADED);
                adapter.setTeams(teams);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                teamsLoadMessage = message;
                teamsLoadStateLiveData.setValue(LoadDataState.ERROR);
            }
        });
    }
}
