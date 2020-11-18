package com.svmc.footballMatching.ui.team.match.team;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.callBackInterface.QueryTeamsCallBack;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.repository.TeamRepository;
import com.svmc.footballMatching.data.session.CurrentTeam;

import java.util.List;

public class NearbyTeamsViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MatchTeamsRecyclerViewAdapter adapter = new MatchTeamsRecyclerViewAdapter();
    private MutableLiveData<LoadDataState> teamsLoadStateLiveData = new MutableLiveData<>(LoadDataState.INIT);
    public String teamsLoadMessage;

    public NearbyTeamsViewModel() {
        queryAllTeams(CurrentTeam.getInstance().getMyTeamLiveData().getValue().getId());
    }

    public MatchTeamsRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public LiveData<LoadDataState> getTeamsLoadStateLiveData() {
        return teamsLoadStateLiveData;
    }

    public void queryAllTeams(String myTeamId) {
        teamsLoadStateLiveData.setValue(LoadDataState.LOADING);
        teamRepository.queryAllTeams(myTeamId, new QueryTeamsCallBack() {
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
