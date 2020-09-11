package com.svmc.footballMatching.ui.team.match;

import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.model.Team;

import java.util.ArrayList;
import java.util.List;

public class MatchTeamViewModel extends ViewModel {
    private TeamsRecyclerViewAdapter adapter = new TeamsRecyclerViewAdapter();

    public TeamsRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void getNearbyTeams() {

    }

    public void getNextNearbyTeamsBatch() {

    }

    public void getSuggestedTeams() {

    }

    public void getNextSuggestedTeamsBatch() {

    }
}
