package com.svmc.footballMatching.data.session;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.svmc.footballMatching.data.model.Team;

public class CurrentTeam {
    private static CurrentTeam instance;

    private CurrentTeam() {
    }

    public static CurrentTeam getInstance() {
        if (instance == null) {
            instance = new CurrentTeam();
        }
        return instance;
    }

    private MutableLiveData<Team> teamLiveData = new MutableLiveData<>();

    public LiveData<Team> getTeamLiveData() {
        return teamLiveData;
    }

    public void setTeam(Team team) {
        teamLiveData.setValue(team);
    }
}
