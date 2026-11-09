package com.seamus.eventticketingapi.event;

import java.time.Instant;

public class Event {
    private final Long id;
    private String name;
    private String venueName;
    private Instant startsAt;
    private int capacity;
    private long priceCents;

    public Event(Long id, String eventName, String venueName, Instant startsAt, int capacity, long priceCents) {
        this.id = id;
        this.name = eventName;
        this.venueName = venueName;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.priceCents = priceCents;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVenueName() {
        return venueName;
    }

    public Instant getStartsAt() {
        return startsAt;
    }

    public int getCapacity() {
        return capacity;
    }

    public long getPriceCents() {
        return priceCents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setStartsAt(Instant startsAt) {
        this.startsAt = startsAt;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPriceCents(long priceCents) {
        this.priceCents = priceCents;
    }
}
