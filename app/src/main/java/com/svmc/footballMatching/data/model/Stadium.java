package com.svmc.footballMatching.data.model;

import java.util.ArrayList;
import java.util.List;

public class Stadium {
    private String id;
    private String name;
    private Location location;
    private List<Integer> numberOfPlayerEachTeamSupport = new ArrayList<>();
    private long pricePerHour;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Integer> getNumberOfPlayerEachTeamSupport() {
        return numberOfPlayerEachTeamSupport;
    }

    public void setNumberOfPlayerEachTeamSupport(List<Integer> numberOfPlayerEachTeamSupport) {
        this.numberOfPlayerEachTeamSupport = numberOfPlayerEachTeamSupport;
    }

    public long getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(long pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
