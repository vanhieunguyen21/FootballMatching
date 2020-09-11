package com.svmc.footballMatching.ui.team.teamProfile;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.session.CurrentTeam;

public class TeamProfileViewModel extends ViewModel {
    public LiveData<Team> teamLiveData = Transformations.map(CurrentTeam.getInstance().getTeamLiveData(), new Function<Team, Team>() {
        @Override
        public Team apply(Team input) {
            return input;
        }
    });
    private TeamMemberRecyclerViewAdapter teamMemberRecyclerViewAdapter = new TeamMemberRecyclerViewAdapter();

    public TeamMemberRecyclerViewAdapter getTeamMemberRecyclerViewAdapter() {
        return teamMemberRecyclerViewAdapter;
    }
}
