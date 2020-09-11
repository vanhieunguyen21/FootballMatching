package com.svmc.footballMatching.data.model;

import com.google.firebase.Timestamp;
import com.svmc.footballMatching.data.model.user.Referee;

public class Schedule {
    private String id;
    private Timestamp createdTimestamp;
    private Timestamp gameTimestamp;
    private Stadium location;
    private Team team1;
    private Team team2;
    private Referee referee;

    public Schedule() {
    }

    public Schedule(String id, Timestamp createdTimestamp, Timestamp gameTimestamp, Stadium location, Team team1, Team team2, Referee referee) {
        this.id = id;
        this.createdTimestamp = createdTimestamp;
        this.gameTimestamp = gameTimestamp;
        this.location = location;
        this.team1 = team1;
        this.team2 = team2;
        this.referee = referee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Timestamp getGameTimestamp() {
        return gameTimestamp;
    }

    public void setGameTimestamp(Timestamp gameTimestamp) {
        this.gameTimestamp = gameTimestamp;
    }

    public Stadium getLocation() {
        return location;
    }

    public void setLocation(Stadium location) {
        this.location = location;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }
}
