package com.svmc.footballMatching.ui.team.myTeams;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.data.model.User;
import com.svmc.footballMatching.data.session.Session;

import java.util.List;

public class MyTeamsViewModel extends ViewModel {
    public LiveData<List<User.JoinedTeam>> joinedTeamLiveData = Transformations.map(Session.getInstance().getUserLiveData(), new Function<User, List<User.JoinedTeam>>() {
        @Override
        public List<User.JoinedTeam> apply(User input) {
            return input.getJoinedTeams();
        }
    });

    private MyTeamsRecyclerViewAdapter adapter = new MyTeamsRecyclerViewAdapter();

    public MyTeamsRecyclerViewAdapter getAdapter(){
        return adapter;
    }
}
