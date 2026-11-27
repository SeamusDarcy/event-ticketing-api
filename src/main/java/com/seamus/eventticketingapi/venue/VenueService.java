package com.seamus.eventticketingapi.venue;

import org.springframework.stereotype.Service;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue entityGetById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException(id));
    }
}
