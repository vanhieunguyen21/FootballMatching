package com.svmc.footballMatching.data.model;

import com.google.firebase.Timestamp;
import com.svmc.footballMatching.data.model.user.Player;
import com.svmc.footballMatching.data.model.user.Referee;
import com.svmc.footballMatching.data.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String id;
    private Team team1;
    private Team team2;
    private Referee referee;
    private Schedule schedule;
    private int team1Score;
    private int team2Score;
    private List<PenaltyCard> penaltyCards = new ArrayList<>();
    private List<GameRating> gameRatings = new ArrayList<>();
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

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

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

    public List<PenaltyCard> getPenaltyCards() {
        return penaltyCards;
    }

    public void setPenaltyCards(List<PenaltyCard> penaltyCards) {
        this.penaltyCards = penaltyCards;
    }

    public List<GameRating> getGameRatings() {
        return gameRatings;
    }

    public void setGameRatings(List<GameRating> gameRatings) {
        this.gameRatings = gameRatings;
    }

    public List<GameComment> getGameComments() {
        return gameComments;
    }

    public void setGameComments(List<GameComment> gameComments) {
        this.gameComments = gameComments;
    }

    public static class PenaltyCard {
        private Player toPlayer;
        private Team inTeam;
        private String cardType;
        private Timestamp givenTimestamp;

        public PenaltyCard() {
        }

        public PenaltyCard(Player toPlayer, Team inTeam, String cardType, Timestamp givenTimestamp) {
            this.toPlayer = toPlayer;
            this.inTeam = inTeam;
            this.cardType = cardType;
            this.givenTimestamp = givenTimestamp;
        }

        public Player getToPlayer() {
            return toPlayer;
        }

        public void setToPlayer(Player toPlayer) {
            this.toPlayer = toPlayer;
        }

        public Team getInTeam() {
            return inTeam;
        }

        public void setInTeam(Team inTeam) {
            this.inTeam = inTeam;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public Timestamp getGivenTimestamp() {
            return givenTimestamp;
        }

        public void setGivenTimestamp(Timestamp givenTimestamp) {
            this.givenTimestamp = givenTimestamp;
        }
    }

    public static class GameRating {
        private User fromUser;
        private String userType;
        private Team inTeam;
        private double fairPlay;
        private double skills;
        private String comment;

        public GameRating() {
        }

        public GameRating(User fromUser, String userType, Team inTeam, double fairPlay, double skills, String comment) {
            this.fromUser = fromUser;
            this.userType = userType;
            this.inTeam = inTeam;
            this.fairPlay = fairPlay;
            this.skills = skills;
            this.comment = comment;
        }

        public User getFromUser() {
            return fromUser;
        }

        public void setFromUser(User fromUser) {
            this.fromUser = fromUser;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Team getInTeam() {
            return inTeam;
        }

        public void setInTeam(Team inTeam) {
            this.inTeam = inTeam;
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
