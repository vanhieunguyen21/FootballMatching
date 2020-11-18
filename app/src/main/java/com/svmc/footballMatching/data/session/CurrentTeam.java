package com.svmc.footballMatching.data.session;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.svmc.footballMatching.callBackInterface.GetTeamCallBack;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.repository.TeamRepository;
import com.svmc.footballMatching.ui.team.teamProfile.TeamMemberRecyclerViewAdapter;

public class CurrentTeam {
    private static CurrentTeam instance;
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MutableLiveData<Team> myTeamLiveData;
    private MutableLiveData<LoadDataState> teamLoadStateLiveData = new MutableLiveData<>(LoadDataState.INIT);
    private TeamMemberRecyclerViewAdapter teamMemberRecyclerViewAdapter = new TeamMemberRecyclerViewAdapter();
    public String teamLoadMessage;

    private CurrentTeam(Team team) {
        myTeamLiveData = new MutableLiveData<>(team);
        if (team != null) {
            teamLoadStateLiveData.setValue(LoadDataState.LOADING);
            teamRepository.getTeam(team.getId(), new GetTeamCallBack() {
                @Override
                public void onSuccess(Team team) {
                    myTeamLiveData.setValue(team);
                    teamMemberRecyclerViewAdapter.setTeamMembers(team.getTeamMembers());
                    teamMemberRecyclerViewAdapter.notifyDataSetChanged();
                    teamLoadStateLiveData.setValue(LoadDataState.LOADED);
                }

                @Override
                public void onFailure(String message) {
                    teamLoadStateLiveData.setValue(LoadDataState.ERROR);
                    teamLoadMessage = message;
                }
            });
        }
    }

    public static CurrentTeam getInstance() {
        return instance;
    }

    public static void createInstance(@NonNull Team team) {
        instance = new CurrentTeam(team);
    }

    public static void deleteInstance(){
        instance = null;
    }

    public LiveData<Team> getMyTeamLiveData() {
        return myTeamLiveData;
    }

    public LiveData<LoadDataState> getTeamLoadStateLiveData() {
        return teamLoadStateLiveData;
    }


    public void setTeam(Team team) {
        myTeamLiveData.setValue(team);
    }
}
