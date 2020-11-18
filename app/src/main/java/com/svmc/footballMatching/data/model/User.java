package com.svmc.footballMatching.data.model;

import com.google.firebase.Timestamp;
import com.svmc.footballMatching.data.model.Location;
import com.svmc.footballMatching.data.model.Photo;
import com.svmc.footballMatching.data.model.Schedule;
import com.svmc.footballMatching.data.model.Team;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.svmc.footballMatching.util.MapUtils.*;

public class User {
    private String id;
    private String email;
    private String fullName;
    private List<Photo> photos = new ArrayList<>();
    private String address;
    private Timestamp birthday;
    private String phone;
    private Timestamp joinedTimestamp;
    private boolean accountActive;
    private int height;
    private int weight;
    private String introduction;
    private Map<String, Object> preferredPositions = new HashMap<>();
    private List<Schedule> schedules = new ArrayList<>();
    private Location location;
    private Timestamp lastUpdateNotificationTimestamp;
    private List<JoinedTeam> joinedTeams = new ArrayList<>();
    private List<LikedTeam> likedTeams = new ArrayList<>();
    private List<LikedTeam> likedByTeams = new ArrayList<>();

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getJoinedTimestamp() {
        return joinedTimestamp;
    }

    public void setJoinedTimestamp(Timestamp joinedTimestamp) {
        this.joinedTimestamp = joinedTimestamp;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public String getBirthdayString() {
        if (birthday == null) return "";
        Date birthdayDate = birthday.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(birthdayDate);
    }

    public String getJoinedDateString() {
        Date joinedDateDate = joinedTimestamp.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(joinedDateDate);
    }

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

    public Timestamp getLastUpdateNotificationTimestamp() {
        return lastUpdateNotificationTimestamp;
    }

    public void setLastUpdateNotificationTimestamp(Timestamp lastUpdateNotificationTimestamp) {
        this.lastUpdateNotificationTimestamp = lastUpdateNotificationTimestamp;
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

    public boolean isLikedBy(String teamId) {
        for (LikedTeam likedTeam : likedByTeams){
            if (likedTeam.getTeam().getId().equals(teamId)) return true;
        }
        return false;
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
}
