package com.seamus.eventticketingapi.sandbox;

import org.springframework.stereotype.Service;

@Service
public class SimpleGreetingService implements GreetingService {

    @Override
    public String greeting(String name) {
        return "Hello " + name;
    }
}
