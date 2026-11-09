package com.seamus.eventticketingapi.event;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryEventRepository {
    private final Map<Long, Event> events = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public Event save(Event event) {
       if (event.getId() == null) {
           Event savedEvent = new Event(nextId.getAndIncrement(), event.getName(), event.getVenueName(), event.getStartsAt(), event.getCapacity(), event.getPriceCents());
           events.put(savedEvent.getId(), savedEvent);
           return savedEvent;
       } else {
           events.put(event.getId(), event);
           return event;
       }
    }

    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(events.get(id));
    }

    public List<Event> findAll(){
        return events.values().stream()
                .sorted(Comparator.comparingLong(Event::getId))
                .toList();
    }

    public boolean deleteById(Long id){
        return events.remove(id) != null;
    }

}
