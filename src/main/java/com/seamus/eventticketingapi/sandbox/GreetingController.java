package com.seamus.eventticketingapi.sandbox;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(@Qualifier("simpleGreetingService") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/api/greet")
    public String greeting(@RequestParam String name) {
        return greetingService.greeting(name);
    }
}
