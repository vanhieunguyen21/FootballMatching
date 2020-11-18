package com.svmc.footballMatching.callBackInterface;

import com.svmc.footballMatching.data.model.Team;

import java.util.List;

public interface QueryTeamsCallBack {
    void onSuccess(List<Team> teams);
    void onFailure(String message);
}
