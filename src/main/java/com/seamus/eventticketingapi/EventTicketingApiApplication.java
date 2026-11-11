package com.seamus.eventticketingapi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EventTicketingApiApplication {
    private static final Logger log = LoggerFactory.getLogger(EventTicketingApiApplication.class);

     static void main(String[] args) {
         log.info("main: before SpringApplication.run");
         SpringApplication.run(EventTicketingApiApplication.class, args);
         log.info("main: after SpringApplication.run");
    }
}
