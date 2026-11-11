package com.seamus.eventticketingapi.event;

import com.seamus.eventticketingapi.event.dto.CreateEventRequest;
import com.seamus.eventticketingapi.event.dto.EventResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService {
    private final InMemoryEventRepository inMemoryEventRepository;

    public EventService(InMemoryEventRepository inMemoryEventRepository) {
        this.inMemoryEventRepository = inMemoryEventRepository;
    }

    public EventResponse create(CreateEventRequest createEventRequest) {
        Event event = new Event(
                null,
                createEventRequest.name(),
                createEventRequest.venueName(),
                createEventRequest.startsAt(),
                createEventRequest.capacity(),
                createEventRequest.priceCents());

        return eventToResponse(inMemoryEventRepository.save(event));
    }

    public EventResponse getById(Long id) {
        Event event = inMemoryEventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return eventToResponse(event);
    }

    public List<EventResponse> getAll() {
        return inMemoryEventRepository.findAll().stream()
                .map(this::eventToResponse)
                .toList();
    }

    public EventResponse update(Long id, CreateEventRequest createEventRequest) {
        inMemoryEventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id)); //check event exists if not throw
        Event updatedEvent = new Event(
                id,
                createEventRequest.name(),
                createEventRequest.venueName(),
                createEventRequest.startsAt(),
                createEventRequest.capacity(),
                createEventRequest.priceCents());
        inMemoryEventRepository.save(updatedEvent);
        return eventToResponse(updatedEvent);
    }

    public void deleteById(Long id) {
        if (!inMemoryEventRepository.deleteById(id)) {
            throw new EventNotFoundException(id);
        }
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
