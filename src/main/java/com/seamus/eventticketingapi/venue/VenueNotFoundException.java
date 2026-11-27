package com.seamus.eventticketingapi.venue;

public class VenueNotFoundException extends RuntimeException {
    public VenueNotFoundException(Long id) {
        super("Venue " + id + " not found");
    }
}
