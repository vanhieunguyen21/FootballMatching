package com.svmc.footballMatching.data.model.user;

import com.svmc.footballMatching.data.model.Game;
import com.svmc.footballMatching.data.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Referee extends User {
    private List<Schedule> schedules = new ArrayList<>();
    private List<Game> participatedGames = new ArrayList<>();

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Game> getParticipatedGames() {
        return participatedGames;
    }

    public void setParticipatedGames(List<Game> participatedGames) {
        this.participatedGames = participatedGames;
    }
}
