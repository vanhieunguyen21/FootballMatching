package com.svmc.footballMatching.data.repository;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.svmc.footballMatching.callBackInterface.CreateTeamCallBack;
import com.svmc.footballMatching.callBackInterface.GetTeamCallBack;
import com.svmc.footballMatching.callBackInterface.LikePlayerCallBack;
import com.svmc.footballMatching.callBackInterface.LikeTeamCallBack;
import com.svmc.footballMatching.callBackInterface.QueryTeamsCallBack;
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

    public void getTeam(String teamId, GetTeamCallBack callBack) {
        teamDataSource.getTeam(teamId, callBack);
    }

    public void updateTeamProfile(String teamId, Map<String, Object> updateIntroduction, UpdateProfileCallBack callBack) {
        teamDataSource.updateTeamProfile(teamId, updateIntroduction, callBack);
    }

    public void createTeam(String teamName, CreateTeamCallBack createTeamCallBack) {
        teamDataSource.createTeam(teamName, createTeamCallBack);
    }

    public void updateJoinedTeam(String uid) {
        teamDataSource.updateJoinedTeams(uid);
    }

    public void likeTeam(String sourceTeamId, String destinationTeamId, LikeTeamCallBack callBack) {
        teamDataSource.likeTeam(sourceTeamId, destinationTeamId, callBack);
    }

    public void queryAllTeams(String myTeamId, QueryTeamsCallBack callBack) {
        teamDataSource.queryAllTeams(myTeamId, callBack);
    }

    public ListenerRegistration listenToTeamLikedByTeams(String teamId, EventListener<QuerySnapshot> eventListener) {
        return teamDataSource.listenToTeamLikedByTeams(teamId, eventListener);
    }

    public ListenerRegistration listenToTeamLikedByPlayers(String teamId, EventListener<QuerySnapshot> eventListener) {
        return teamDataSource.listenToTeamLikedByPlayers(teamId, eventListener);
    }

    public void updateLastUpdateNotification(String teamId) {
        teamDataSource.updateLastUpdateNotification(teamId);
    }

    public void likePlayer(String sourceTeamId, String playerId, LikePlayerCallBack callBack) {
        teamDataSource.likePlayer(sourceTeamId, playerId, callBack);
    }

    public void queryNotJoinedTeams(String uid, QueryTeamsCallBack callBack) {
        teamDataSource.queryNotJoinedTeams(uid, callBack);
    }


}
