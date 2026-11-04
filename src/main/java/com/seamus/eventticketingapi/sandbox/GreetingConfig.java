package com.seamus.eventticketingapi.sandbox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingConfig {

    @Bean
    public AdvancedGreetingService advancedGreetingService() {
        return new AdvancedGreetingService();
    }
}
