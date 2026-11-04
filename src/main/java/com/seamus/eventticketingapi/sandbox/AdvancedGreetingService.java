package com.seamus.eventticketingapi.sandbox;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


public class AdvancedGreetingService implements GreetingService {

    @Override
    public String greeting(String name) {
        return "Hello " + name + ", How are you?";
    }
}
