package com.seamus.eventticketingapi.event.dto;

import java.time.Instant;

public record EventResponse(
        Long id,
        String name,
        String venueName,
        Instant startsAt,
        Integer capacity,
        long priceCents) {
}
