package com.nesine.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Event {

    private long betradarId;
    private String code;
    private String name;
    private String type;
    private int sportId;
    private double minOdd;
    private double maxOdd;
    private long startDateEpoch;
    private long marketId;
    private String marketName;
    @JsonProperty("isLive")
    private boolean live;
    @JsonProperty("isActive")
    private boolean active;
    private List<Outcome> outcomes;

    public long getBetradarId() {
        return betradarId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getSportId() {
        return sportId;
    }

    public double getMinOdd() {
        return minOdd;
    }

    public double getMaxOdd() {
        return maxOdd;
    }

    public long getStartDateEpoch() {
        return startDateEpoch;
    }

    public long getMarketId() {
        return marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public boolean isLive() {
        return live;
    }

    public boolean isActive() {
        return active;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }
}
