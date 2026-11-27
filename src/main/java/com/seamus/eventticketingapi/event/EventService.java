package com.seamus.eventticketingapi.event;

import com.seamus.eventticketingapi.event.dto.CreateEventRequest;
import com.seamus.eventticketingapi.event.dto.EventResponse;
import com.seamus.eventticketingapi.venue.VenueNotFoundException;
import com.seamus.eventticketingapi.venue.VenueRepository;
import com.seamus.eventticketingapi.venue.VenueService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueService venueService;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueService venueService, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueService = venueService;
        this.venueRepository = venueRepository;
    }

    public EventResponse create(CreateEventRequest createEventRequest) {
        Event event = new Event(
                createEventRequest.name(),
                venueService.entityGetById(createEventRequest.venueId()),
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

    @Transactional
    public EventResponse update(Long id, CreateEventRequest createEventRequest) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        event.update(
                createEventRequest.name(),
                venueService.entityGetById(createEventRequest.venueId()),
                createEventRequest.startsAt(),
                createEventRequest.capacity(),
                createEventRequest.priceCents()
        );
        return eventToResponse(event);
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
                event.getVenue().getId(),
                event.getStartsAt(),
                event.getCapacity(),
                event.getRemaining(),
                event.getPriceCents());
    }
}
