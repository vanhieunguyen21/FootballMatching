package com.svmc.footballMatching.ui.team.match.team;

import androidx.lifecycle.ViewModel;

public class MatchTeamViewModel extends ViewModel {
    private MatchTeamsRecyclerViewAdapter adapter = new MatchTeamsRecyclerViewAdapter();

    public MatchTeamsRecyclerViewAdapter getAdapter() {
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
