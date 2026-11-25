package com.seamus.eventticketingapi.event.dto;

import java.time.Instant;

public record EventResponse(
        Long id,
        String name,
        Long venueId,
        Instant startsAt,
        Integer capacity,
        Long priceCents) {
}
