package com.nesine.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

public class Coupon {

    private String couponId;
    private String couponHash;
    private int eventCount;
    private int count;
    private int count1H;
    private int count24H;
    private int countLast1H;
    private int countLast24H;
    private int orderIndex;
    private int multiply;
    private String totalOdd;
    @JsonProperty("isWon")
    private boolean won;
    @JsonProperty("isIncludeUvEvent")
    private boolean includeUvEvent;
    private OffsetDateTime minEventDate;
    private OffsetDateTime playedDate;
    private OffsetDateTime lastPlayedDate;
    private OffsetDateTime creationDate;
    private List<Event> events;

    public String getCouponId() {
        return couponId;
    }

    public String getCouponHash() {
        return couponHash;
    }

    public int getEventCount() {
        return eventCount;
    }

    public int getCount() {
        return count;
    }

    public int getCount1H() {
        return count1H;
    }

    public int getCount24H() {
        return count24H;
    }

    public int getCountLast1H() {
        return countLast1H;
    }

    public int getCountLast24H() {
        return countLast24H;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public int getMultiply() {
        return multiply;
    }

    public String getTotalOdd() {
        return totalOdd;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isIncludeUvEvent() {
        return includeUvEvent;
    }

    public OffsetDateTime getMinEventDate() {
        return minEventDate;
    }

    public OffsetDateTime getPlayedDate() {
        return playedDate;
    }

    public OffsetDateTime getLastPlayedDate() {
        return lastPlayedDate;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public List<Event> getEvents() {
        return events;
    }
}
