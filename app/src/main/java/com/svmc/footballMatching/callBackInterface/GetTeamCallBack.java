package com.svmc.footballMatching.callBackInterface;

import com.svmc.footballMatching.data.model.Team;

public interface GetTeamCallBack {
    void onSuccess(Team team);

    void onFailure(String message);
}
