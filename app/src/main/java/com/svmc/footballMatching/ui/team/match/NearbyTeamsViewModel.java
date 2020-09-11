package com.svmc.footballMatching.ui.team.match;

import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.model.Team;

import java.util.ArrayList;
import java.util.List;

public class NearbyTeamsViewModel extends ViewModel {
    private TeamsRecyclerViewAdapter adapter = new TeamsRecyclerViewAdapter();

    public TeamsRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public NearbyTeamsViewModel() {
        init();
    }

    public void init() {
        List<Team> teams = new ArrayList<>();
        Team team = new Team();
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        teams.add(team);
        adapter.setTeams(teams);
    }
}
