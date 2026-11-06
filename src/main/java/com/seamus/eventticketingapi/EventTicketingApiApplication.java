package com.seamus.eventticketingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class    EventTicketingApiApplication {

     static void main(String[] args) {
        SpringApplication.run(EventTicketingApiApplication.class, args);
    }

}
