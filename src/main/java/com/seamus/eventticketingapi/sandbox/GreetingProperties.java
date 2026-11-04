package com.seamus.eventticketingapi.sandbox;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "greeting")
public record GreetingProperties(String prefix) {}
