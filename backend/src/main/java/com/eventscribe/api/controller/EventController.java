
package com.eventscribe.api.controller;

import com.eventscribe.api.dto.EventDto;
import com.eventscribe.api.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents(Authentication authentication) {
        return ResponseEntity.ok(eventService.getAllEventsForUser(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable String id, Authentication authentication) {
        return ResponseEntity.ok(eventService.getEvent(id, authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto, Authentication authentication) {
        return ResponseEntity.ok(eventService.createEvent(eventDto, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable String id, @RequestBody EventDto eventDto, Authentication authentication) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDto, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id, Authentication authentication) {
        eventService.deleteEvent(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
