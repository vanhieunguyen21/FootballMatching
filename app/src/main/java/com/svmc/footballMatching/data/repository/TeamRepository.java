package com.svmc.footballMatching.data.repository;

import com.svmc.footballMatching.callBackInterface.UpdateProfileCallBack;
import com.svmc.footballMatching.data.dataSource.fireStore.TeamDataSource;

import java.util.Map;

public class TeamRepository {
    private static TeamRepository instance;
    private TeamDataSource teamDataSource = TeamDataSource.getInstance();

    private TeamRepository() {
    }

    public static TeamRepository getInstance() {
        if (instance == null) {
            instance = new TeamRepository();
        }
        return instance;
    }

    public void createTeam() {
    }

    public void getTeam(String teamId) {
    }

    public void updateTeamProfile(String teamId, Map<String, Object> updateIntroduction, UpdateProfileCallBack callBack) {
        teamDataSource.updateTeamProfile(teamId, updateIntroduction, callBack);
    }
}
