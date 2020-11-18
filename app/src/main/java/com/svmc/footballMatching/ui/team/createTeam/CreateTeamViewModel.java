package com.svmc.footballMatching.ui.team.createTeam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.svmc.footballMatching.callBackInterface.CreateTeamCallBack;
import com.svmc.footballMatching.data.enumeration.Result;
import com.svmc.footballMatching.data.repository.TeamRepository;

public class CreateTeamViewModel extends ViewModel {
    private TeamRepository teamRepository = TeamRepository.getInstance();

    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void createTeam(String teamName){
        teamRepository.createTeam(teamName, new CreateTeamCallBack(){
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
