package com.svmc.footballMatching.data.model.user;

import com.google.firebase.Timestamp;
import com.svmc.footballMatching.data.model.Location;
import com.svmc.footballMatching.data.model.Schedule;
import com.svmc.footballMatching.data.model.Team;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.svmc.footballMatching.util.MapUtils.*;

public class Player extends User {
    private int height;
    private int weight;
    private String introduction;
    private Map<String, Object> preferredPositions = new HashMap<>();
    private List<Schedule> schedules = new ArrayList<>();
    private Location location;
    private List<JoinedTeam> joinedTeams = new ArrayList<>();
    private List<LikedTeam> likedTeams = new ArrayList<>();
    private List<LikedTeam> likedByTeams = new ArrayList<>();

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Map<String, Object> getPreferredPositions() {
        return preferredPositions;
    }

    public void setPreferredPositions(Map<String, Object> preferredPositions) {
        this.preferredPositions = preferredPositions;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<JoinedTeam> getJoinedTeams() {
        return joinedTeams;
    }

    public void setJoinedTeams(List<JoinedTeam> joinedTeams) {
        this.joinedTeams = joinedTeams;
    }

    public List<LikedTeam> getLikedTeams() {
        return likedTeams;
    }

    public void setLikedTeams(List<LikedTeam> likedTeams) {
        this.likedTeams = likedTeams;
    }

    public List<LikedTeam> getLikedByTeams() {
        return likedByTeams;
    }

    public void setLikedByTeams(List<LikedTeam> likedByTeams) {
        this.likedByTeams = likedByTeams;
    }

    public String getHeightStringMeter() {
        return String.format(Locale.getDefault(), "%.2f m", ((double) height / 100));
    }

    public String getWeightStringKilogram() {
        return String.format(Locale.getDefault(), "%.2f kg", ((double) weight / 1000));
    }

    public boolean isForwardAttacker() {
        return valueToBooleanOrFalse(preferredPositions, "forwardAttacker");
    }


    public boolean isCentreAttacker() {
        return valueToBooleanOrFalse(preferredPositions, "centreAttacker");
    }


    public boolean isDefender() {
        return valueToBooleanOrFalse(preferredPositions, "defender");
    }


    public boolean isAttackingMidfielder() {
        return valueToBooleanOrFalse(preferredPositions, "attackingMidfielder");
    }


    public boolean isCentreMidfielder() {
        return valueToBooleanOrFalse(preferredPositions, "centreMidfielder");
    }


    public boolean isDefensiveMidfielder() {
        return valueToBooleanOrFalse(preferredPositions, "defensiveMidfielder");
    }


    public boolean isGoalkeeper() {
        return valueToBooleanOrFalse(preferredPositions, "goalkeeper");
    }

    public static class JoinedTeam {
        private Team team;
        private Timestamp joinedTimestamp;
        private String status;
        private Timestamp leftTimestamp;
        private Timestamp kickedTimestamp;

        public JoinedTeam() {
        }

        public JoinedTeam(Team team, Timestamp joinedTimestamp, String status, Timestamp leftTimestamp, Timestamp kickedTimestamp) {
            this.team = team;
            this.joinedTimestamp = joinedTimestamp;
            this.status = status;
            this.leftTimestamp = leftTimestamp;
            this.kickedTimestamp = kickedTimestamp;
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        public Timestamp getJoinedTimestamp() {
            return joinedTimestamp;
        }

        public void setJoinedTimestamp(Timestamp joinedTimestamp) {
            this.joinedTimestamp = joinedTimestamp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Timestamp getLeftTimestamp() {
            return leftTimestamp;
        }

        public void setLeftTimestamp(Timestamp leftTimestamp) {
            this.leftTimestamp = leftTimestamp;
        }

        public Timestamp getKickedTimestamp() {
            return kickedTimestamp;
        }

        public void setKickedTimestamp(Timestamp kickedTimestamp) {
            this.kickedTimestamp = kickedTimestamp;
        }
    }

    public static class LikedTeam {
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
}
