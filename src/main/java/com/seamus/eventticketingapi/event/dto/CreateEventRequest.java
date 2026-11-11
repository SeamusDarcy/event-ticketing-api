package com.seamus.eventticketingapi.event.dto;

import jakarta.validation.constraints.*;

import java.time.Instant;

public record CreateEventRequest(
        @NotBlank(message =  "name is required") String name,
        String venueName,
        @NotNull(message = "time is required") @Future(message = "time must be in future") Instant startsAt,
        @NotNull(message = "capacity is required") @Positive(message = "capacity must be positive") Integer capacity,
        @NotNull(message = "price is required") @PositiveOrZero(message = "price must be positive") Long priceCents) {
}
