package com.seamus.eventticketingapi.sandbox;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingConfig {

    @Bean
    public AdvancedGreetingService advancedGreetingService(GreetingProperties properties) {
        return new AdvancedGreetingService(properties.prefix());
    }
}
