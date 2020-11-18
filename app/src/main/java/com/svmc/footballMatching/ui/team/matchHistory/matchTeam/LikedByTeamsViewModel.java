package com.svmc.footballMatching.ui.team.matchHistory.matchTeam;

import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.model.LikedTeam;

import java.util.List;

public class LikedByTeamsViewModel extends ViewModel {
    private MatchTeamRecyclerViewAdapter adapter = new MatchTeamRecyclerViewAdapter();

    public MatchTeamRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapterData(List<LikedTeam> likedTeams){
        adapter.setLikedTeams(likedTeams);
        adapter.notifyDataSetChanged();
    }
}
