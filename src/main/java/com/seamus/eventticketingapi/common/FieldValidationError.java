package com.seamus.eventticketingapi.common;

public record FieldValidationError(
        String field,
        String message) {
}
