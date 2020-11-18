package com.svmc.footballMatching.data.model;

import com.google.firebase.Timestamp;

public class Schedule {
    private String id;
    private Timestamp createdTimestamp;
    private Timestamp gameTimestamp;
    private Stadium stadium;
    private Team team1;
    private Team team2;

    public Schedule() {
    }

    public Schedule(String id, Timestamp createdTimestamp, Timestamp gameTimestamp, Stadium stadium, Team team1, Team team2) {
        this.id = id;
        this.createdTimestamp = createdTimestamp;
        this.gameTimestamp = gameTimestamp;
        this.stadium = stadium;
        this.team1 = team1;
        this.team2 = team2;
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

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
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

}
