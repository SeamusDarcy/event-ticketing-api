package com.seamus.eventticketingapi.venue;

public class CapacityTooLowException extends RuntimeException {
    public CapacityTooLowException(int capacity) {
        super("capacity of " + capacity + " is too low");
    }
}
