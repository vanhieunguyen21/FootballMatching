package com.svmc.footballMatching.ui.team.teamProfile;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.svmc.footballMatching.callBackInterface.UpdateProfileCallBack;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.model.Team;
import com.svmc.footballMatching.data.repository.TeamRepository;
import com.svmc.footballMatching.data.session.CurrentTeam;

import java.util.Map;

public class EditTeamIntroductionViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();
    public LiveData<Team> teamLiveData = Transformations.map(CurrentTeam.getInstance().getTeamLiveData(), new Function<Team, Team>() {
        @Override
        public Team apply(Team input) {
            return input;
        }
    });

    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void updateTeamProfile(Map<String, Object> updateIntroduction) {
        teamRepository.updateTeamProfile(teamLiveData.getValue().getId(), updateIntroduction, new UpdateProfileCallBack() {
            @Override
            public void onSuccess() {
                resultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }
}
