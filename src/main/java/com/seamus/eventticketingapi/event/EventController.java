package com.seamus.eventticketingapi.event;

import ch.qos.logback.core.boolex.EventEvaluatorBase;
import com.seamus.eventticketingapi.event.dto.CreateEventRequest;
import com.seamus.eventticketingapi.event.dto.EventResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.UIResource;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public EventResponse getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @GetMapping
    public List<EventResponse> getAll() {
        return eventService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // method returns void but Spring sends the status No_Content because of this Annotation^
    public void deleteById(@PathVariable Long id) {
        eventService.deleteById(id);
    }

    @PutMapping("/{id}")
    public EventResponse updateById(@PathVariable Long id, @Valid @RequestBody CreateEventRequest request) {
        return eventService.update(id, request);
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody CreateEventRequest request) {
        EventResponse eventResponse = eventService.create(request);
        URI location = URI.create("/api/events/" + eventResponse.id());
        //ResponseEntity.create starts new response with 201, puts location in location header and adds body
        return ResponseEntity.created(location).body(eventResponse);
    }
}
