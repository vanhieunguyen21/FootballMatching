package com.svmc.footballMatching.data.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Team implements Serializable {
    private String id;
    private String name;
    private String avatar;
    private List<Photo> photos = new ArrayList<>();
    private List<String> contactTelephones = new ArrayList<>();
    private List<String> contactEmails = new ArrayList<>();
    private Location location;
    private String introduction;
    private String status;
    private int mainGroup;
    private List<Integer> groups;
    private User leader;
    private List<TeamMember> teamMembers = new ArrayList<>();
    private List<Schedule> schedules = new ArrayList<>();
    private List<PlayedGame> gamesHistory = new ArrayList<>();
    private Statistics statistics;
    private List<LikedTeam> likedTeams = new ArrayList<>();
    private List<LikedTeam> likedByTeams = new ArrayList<>();
    private Timestamp lastUpdateNotificationTimestamp;
    private List<LikedPlayer> likedPlayers = new ArrayList<>();
    private List<LikedPlayer> likedByPlayers = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<String> getContactTelephones() {
        return contactTelephones;
    }

    public void setContactTelephones(List<String> contactTelephones) {
        this.contactTelephones = contactTelephones;
    }

    public List<String> getContactEmails() {
        return contactEmails;
    }

    public void setContactEmails(List<String> contactEmails) {
        this.contactEmails = contactEmails;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(int mainGroup) {
        this.mainGroup = mainGroup;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public void setGroups(List<Integer> groups) {
        this.groups = groups;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<PlayedGame> getGamesHistory() {
        return gamesHistory;
    }

    public void setGamesHistory(List<PlayedGame> gamesHistory) {
        this.gamesHistory = gamesHistory;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
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

    public List<LikedPlayer> getLikedPlayers() {
        return likedPlayers;
    }

    public void setLikedPlayers(List<LikedPlayer> likedPlayers) {
        this.likedPlayers = likedPlayers;
    }

    public List<LikedPlayer> getLikedByPlayers() {
        return likedByPlayers;
    }

    public void setLikedByPlayers(List<LikedPlayer> likedByPlayers) {
        this.likedByPlayers = likedByPlayers;
    }

    public boolean isLikedByTeam(String teamId) {
        for (LikedTeam likedTeam : likedByTeams) {
            if (likedTeam.getTeam().getId().equals(teamId)) return true;
        }
        return false;
    }

    public boolean isLikedByPlayer(String uid) {
        for (LikedPlayer likedPlayer : likedByPlayers) {
            if (likedPlayer.getUser().getId().equals(uid)) return true;
        }
        return false;
    }

    public boolean isLeader(String uid) {
        return leader.getId().equals(uid);
    }

    public boolean isPlayerInTeam(String uid) {
        for (TeamMember teamMember : teamMembers) {
            if (teamMember.getStatus().equals("joined") && teamMember.getPlayer().getId().equals(uid))
                return true;
        }
        return false;
    }

    public static class TeamMember {
        private User player;
        private String title;
        private Timestamp invitedTimestamp;
        private Timestamp joinedTimestamp;
        private String status;
        private Timestamp leftTimestamp;
        private Timestamp kickedTimestamp;
        private int showOrder;

        public TeamMember() {
        }

        public TeamMember(User player, String title,
                          Timestamp invitedTimestamp, Timestamp joinedTimestamp,
                          String status, Timestamp leftTimestamp,
                          Timestamp kickedTimestamp, int showOrder) {
            this.player = player;
            this.title = title;
            this.invitedTimestamp = invitedTimestamp;
            this.joinedTimestamp = joinedTimestamp;
            this.status = status;
            this.leftTimestamp = leftTimestamp;
            this.kickedTimestamp = kickedTimestamp;
            this.showOrder = showOrder;
        }

        public User getPlayer() {
            return player;
        }

        public void setPlayer(User player) {
            this.player = player;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Timestamp getInvitedTimestamp() {
            return invitedTimestamp;
        }

        public void setInvitedTimestamp(Timestamp invitedTimestamp) {
            this.invitedTimestamp = invitedTimestamp;
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

        public int getShowOrder() {
            return showOrder;
        }

        public void setShowOrder(int showOrder) {
            this.showOrder = showOrder;
        }
    }

    public static class PlayedGame {
        private Game game;
        private double skills;
        private double fairPlay;
        private double matchRating;

        public Game getGame() {
            return game;
        }

        public void setGame(Game game) {
            this.game = game;
        }

        public double getSkills() {
            return skills;
        }

        public void setSkills(double skills) {
            this.skills = skills;
        }

        public double getFairPlay() {
            return fairPlay;
        }

        public void setFairPlay(double fairPlay) {
            this.fairPlay = fairPlay;
        }

        public double getMatchRating() {
            return matchRating;
        }

        public void setMatchRating(double matchRating) {
            this.matchRating = matchRating;
        }
    }

    public static class Statistics {
        private double skills;
        private double fairPlay;
        private int numberOfMatches;
        private int numberOfWins;
        private int numberOfLosses;
        private int winStreak;
        private int loseStreak;
        private double matchRating;

        public Statistics() {
        }

        public Statistics(double skills, double fairPlay, int numberOfMatches, int numberOfWins, int numberOfLosses, int winStreak, int loseStreak, double matchRating) {
            this.skills = skills;
            this.fairPlay = fairPlay;
            this.numberOfMatches = numberOfMatches;
            this.numberOfWins = numberOfWins;
            this.numberOfLosses = numberOfLosses;
            this.winStreak = winStreak;
            this.loseStreak = loseStreak;
            this.matchRating = matchRating;
        }

        public double getSkills() {
            return skills;
        }

        public void setSkills(double skills) {
            this.skills = skills;
        }

        public double getFairPlay() {
            return fairPlay;
        }

        public void setFairPlay(double fairPlay) {
            this.fairPlay = fairPlay;
        }

        public int getNumberOfMatches() {
            return numberOfMatches;
        }

        public void setNumberOfMatches(int numberOfMatches) {
            this.numberOfMatches = numberOfMatches;
        }

        public int getNumberOfWins() {
            return numberOfWins;
        }

        public void setNumberOfWins(int numberOfWins) {
            this.numberOfWins = numberOfWins;
        }

        public int getNumberOfLosses() {
            return numberOfLosses;
        }

        public void setNumberOfLosses(int numberOfLosses) {
            this.numberOfLosses = numberOfLosses;
        }

        public int getWinStreak() {
            return winStreak;
        }

        public void setWinStreak(int winStreak) {
            this.winStreak = winStreak;
        }

        public int getLoseStreak() {
            return loseStreak;
        }

        public void setLoseStreak(int loseStreak) {
            this.loseStreak = loseStreak;
        }

        public double getMatchRating() {
            return matchRating;
        }

        public void setMatchRating(double matchRating) {
            this.matchRating = matchRating;
        }

        public String getSkillsString() {
            return String.format(Locale.getDefault(), "%.2f", skills);
        }

        public String getFairPlayString() {
            return String.format(Locale.getDefault(), "%.2f", fairPlay);
        }
    }

    public static class LikedPlayer {
        private User user;
        private boolean liked;
        private Timestamp actionTimestamp;

        public LikedPlayer() {
        }

        public LikedPlayer(User user, boolean liked, Timestamp actionTimestamp) {
            this.user = user;
            this.liked = liked;
            this.actionTimestamp = actionTimestamp;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
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
