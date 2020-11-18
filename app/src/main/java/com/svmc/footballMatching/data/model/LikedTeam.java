package com.svmc.footballMatching.data.model;

import com.google.firebase.Timestamp;

public class LikedTeam {
    private Team team;
    private boolean liked;
    private Timestamp actionTimestamp;

    public LikedTeam() {
    }

    public LikedTeam(Team team, boolean liked, Timestamp actionTimestamp) {
        this.team = team;
        this.liked = liked;
        this.actionTimestamp = actionTimestamp;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public Timestamp getActionTimestamp() {
        return actionTimestamp;
    }

    public void setActionTimestamp(Timestamp actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
    }
}
