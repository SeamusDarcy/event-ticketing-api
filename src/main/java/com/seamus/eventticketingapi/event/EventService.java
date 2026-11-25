package com.seamus.eventticketingapi.event;

import com.seamus.eventticketingapi.event.dto.CreateEventRequest;
import com.seamus.eventticketingapi.event.dto.EventResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventResponse create(CreateEventRequest createEventRequest) {
        Event event = new Event(
                null,
                createEventRequest.name(),
                createEventRequest.venueName(),
                createEventRequest.startsAt(),
                createEventRequest.capacity(),
                createEventRequest.priceCents());

        return eventToResponse(eventRepository.save(event));
    }

    public EventResponse getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return eventToResponse(event);
    }

    public List<EventResponse> getAll() {
        return eventRepository.findAll().stream()
                .map(this::eventToResponse)
                .toList();
    }

    public EventResponse update(Long id, CreateEventRequest createEventRequest) {
        eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id)); //check event exists if not throw
        Event updatedEvent = new Event(
                id,
                createEventRequest.name(),
                createEventRequest.venueName(),
                createEventRequest.startsAt(),
                createEventRequest.capacity(),
                createEventRequest.priceCents());
        eventRepository.save(updatedEvent);
        return eventToResponse(updatedEvent);
    }

    public void deleteById(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        eventRepository.deleteById(id);
    }

    private EventResponse eventToResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getVenueName(),
                event.getStartsAt(),
                event.getCapacity(),
                event.getPriceCents());
    }
}
