package com.svmc.footballMatching.data.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Game {
    private String id;
    private Team team1;
    private Team team2;
    private Schedule schedule;
    @Nullable
    private Integer team1Score;
    @Nullable
    private Integer team2Score;
    private GameRating team1Rating;
    private GameRating team2Rating;
    private List<GameComment> gameComments = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    public GameRating getTeam1Rating() {
        return team1Rating;
    }

    public void setTeam1Rating(GameRating team1Rating) {
        this.team1Rating = team1Rating;
    }

    public GameRating getTeam2Rating() {
        return team2Rating;
    }

    public void setTeam2Rating(GameRating team2Rating) {
        this.team2Rating = team2Rating;
    }

    public List<GameComment> getGameComments() {
        return gameComments;
    }

    public void setGameComments(List<GameComment> gameComments) {
        this.gameComments = gameComments;
    }

    public static class GameRating {
        private int team1Score;
        private int team2Score;
        private double fairPlay;
        private double skills;
        private String comment;

        public int getTeam1Score() {
            return team1Score;
        }

        public void setTeam1Score(int team1Score) {
            this.team1Score = team1Score;
        }

        public int getTeam2Score() {
            return team2Score;
        }

        public void setTeam2Score(int team2Score) {
            this.team2Score = team2Score;
        }

        public double getFairPlay() {
            return fairPlay;
        }

        public void setFairPlay(double fairPlay) {
            this.fairPlay = fairPlay;
        }

        public double getSkills() {
            return skills;
        }

        public void setSkills(double skills) {
            this.skills = skills;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public static class GameComment {
        private String id;
        private User user;
        private String commentType;
        private String text;
        private String imageUrl;
        private String attachmentUrl;
        private Timestamp sentTimestamp;
        private String status;

        public GameComment() {
        }

        public GameComment(String id, User user, String commentType, String text, String imageUrl, String attachmentUrl, Timestamp sentTimestamp, String status) {
            this.id = id;
            this.user = user;
            this.commentType = commentType;
            this.text = text;
            this.imageUrl = imageUrl;
            this.attachmentUrl = attachmentUrl;
            this.sentTimestamp = sentTimestamp;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getCommentType() {
            return commentType;
        }

        public void setCommentType(String commentType) {
            this.commentType = commentType;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getAttachmentUrl() {
            return attachmentUrl;
        }

        public void setAttachmentUrl(String attachmentUrl) {
            this.attachmentUrl = attachmentUrl;
        }

        public Timestamp getSentTimestamp() {
            return sentTimestamp;
        }

        public void setSentTimestamp(Timestamp sentTimestamp) {
            this.sentTimestamp = sentTimestamp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
