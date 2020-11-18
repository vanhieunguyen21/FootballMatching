package com.svmc.footballMatching.ui.team.match.team;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.callBackInterface.GetTeamCallBack;
import com.svmc.footballMatching.callBackInterface.LikeTeamCallBack;
import com.svmc.footballMatching.data.enumeration.LoadDataState;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.repository.TeamRepository;
import com.svmc.footballMatching.data.session.CurrentTeam;
import com.svmc.footballMatching.ui.team.teamProfile.TeamMemberRecyclerViewAdapter;

public class TeamProfileViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    private MutableLiveData<Team> teamLiveData;
    private TeamMemberRecyclerViewAdapter teamMemberRecyclerViewAdapter = new TeamMemberRecyclerViewAdapter();
    private MutableLiveData<LoadDataState> teamLoadState = new MutableLiveData<>(LoadDataState.INIT);
    public String teamLoadMessage;
    private MutableLiveData<Result> likeTeamResultLiveData = new MutableLiveData<>();
    public String likeTeamResultMessage = null;

    public TeamProfileViewModel(Team team) {
        teamLiveData = new MutableLiveData<>();
        teamLoadState.setValue(LoadDataState.LOADING);
        teamRepository.getTeam(team.getId(), new GetTeamCallBack() {
            @Override
            public void onSuccess(Team team) {
                teamLiveData.setValue(team);
                teamMemberRecyclerViewAdapter.setTeamMembers(team.getTeamMembers());
                teamMemberRecyclerViewAdapter.notifyDataSetChanged();
                teamLoadState.setValue(LoadDataState.LOADED);
            }

            @Override
            public void onFailure(String message) {
                teamLoadState.setValue(LoadDataState.ERROR);
                teamLoadMessage = message;
            }
        });
    }

    public LiveData<Team> getTeamLiveData() {
        return teamLiveData;
    }

    public LiveData<LoadDataState> getTeamLoadState() {
        return teamLoadState;
    }

    public TeamMemberRecyclerViewAdapter getTeamMemberRecyclerViewAdapter() {
        return teamMemberRecyclerViewAdapter;
    }

    public LiveData<Result> getLikeTeamResultLiveData() {
        return likeTeamResultLiveData;
    }

    public void likeTeam() {
        String sourceTeamId = CurrentTeam.getInstance().getMyTeamLiveData().getValue().getId();
        String destinationTeamId = teamLiveData.getValue().getId();
        teamRepository.likeTeam(sourceTeamId, destinationTeamId, new LikeTeamCallBack() {
            @Override
            public void onSuccess() {
                likeTeamResultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                likeTeamResultMessage = message;
                likeTeamResultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public static class TeamProfileViewModelFactory implements ViewModelProvider.Factory {
        private Team team;

        public TeamProfileViewModelFactory(Team team) {
            this.team = team;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(TeamProfileViewModel.class)) {
                return (T) new TeamProfileViewModel(team);
            } else {
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }
    }
}
