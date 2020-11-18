package com.svmc.footballMatching.ui.team.matchHistory;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.data.model.Team;

public class MatchHistoryViewModel extends ViewModel {
    private MutableLiveData<Team> teamLiveData;

    public MatchHistoryViewModel(Team team) {
        teamLiveData = new MutableLiveData<>(team);
    }

    public static class MatchHistoryViewModelFactory implements ViewModelProvider.Factory {
        private Team team;

        public MatchHistoryViewModelFactory(Team team) {
            this.team = team;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MatchHistoryViewModel.class)) {
                return (T) new MatchHistoryViewModel(team);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
