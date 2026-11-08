package com.seamus.eventticketingapi.sandbox;

import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SimpleGreetingService implements GreetingService {
    private static final Logger log = LoggerFactory.getLogger(SimpleGreetingService.class);

    public SimpleGreetingService() {
        log.info("SimpleGreetingService : constructor");
    }

    @PostConstruct
    public void init() {
        log.info("SimpleGreetingService : init");
    }

    @Override
    public String greeting(String name) {
        return "Hello " + name;
    }
}
