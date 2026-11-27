package com.seamus.eventticketingapi.event;

import com.seamus.eventticketingapi.venue.CapacityTooLowException;
import com.seamus.eventticketingapi.venue.Venue;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(nullable = false)
    private Instant startsAt;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private long priceCents;

    @Column(nullable = false)
    private int remaining;

    protected Event() {
    }

    public Event(String name, Venue venue, Instant startsAt, int capacity, long priceCents) {
        this.name = name;
        this.venue = venue;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.priceCents = priceCents;
        this.remaining = capacity;
    }

    public void update(String name, Venue venue, Instant startsAt, int capacity, long priceCents){
        int newRemaining = this.remaining + (capacity - this.capacity);

        if (newRemaining < 0){
            throw new CapacityTooLowException(capacity);
        }
        this.name = name;
        this.venue = venue;
        this.startsAt = startsAt;
        this.capacity = capacity;
        this.remaining = newRemaining;
        this.priceCents = priceCents;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Venue getVenue() {
        return venue;
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

    public int getRemaining() {
        return remaining;
    }
}
