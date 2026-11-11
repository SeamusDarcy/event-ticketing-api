package com.seamus.eventticketingapi.event;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(Long id){
        super("Event " + id + " not found");
    }
}
