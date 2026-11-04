package com.seamus.eventticketingapi.sandbox;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


public class AdvancedGreetingService implements GreetingService {
    private final String value;

    public AdvancedGreetingService(String value) {
        this.value = value;
    }
    @Override
    public String greeting(String name) {
        return value + ", " + name + ", How are you?";
    }
}
