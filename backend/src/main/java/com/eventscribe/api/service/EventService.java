
package com.eventscribe.api.service;

import com.eventscribe.api.dto.EventDto;
import com.eventscribe.api.model.Event;
import com.eventscribe.api.model.User;
import com.eventscribe.api.repository.EventRepository;
import com.eventscribe.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<EventDto> getAllEventsForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return eventRepository.findByCreatedBy(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EventDto getEvent(String id, String email) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!event.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to access this event");
        }
        
        return convertToDto(event);
    }

    public EventDto createEvent(EventDto eventDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Event event = convertToEntity(eventDto);
        event.setCreatedBy(user);
        
        return convertToDto(eventRepository.save(event));
    }

    public EventDto updateEvent(String id, EventDto eventDto, String email) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!existingEvent.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to modify this event");
        }
        
        // Update event fields
        Event updatedEvent = convertToEntity(eventDto);
        updatedEvent.setId(id);
        updatedEvent.setCreatedBy(user);
        
        return convertToDto(eventRepository.save(updatedEvent));
    }

    public void deleteEvent(String id, String email) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!event.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to delete this event");
        }
        
        eventRepository.delete(event);
    }

    private EventDto convertToDto(Event event) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartDate().format(formatter),
                event.getEndDate().format(formatter),
                event.getLocation(),
                event.getCategory(),
                event.isAllDay(),
                event.getColor()
        );
    }

    private Event convertToEntity(EventDto eventDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        
        Event event = new Event();
        if (eventDto.getId() != null) {
            event.setId(eventDto.getId());
        }
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(LocalDateTime.parse(eventDto.getStartDate(), formatter));
        event.setEndDate(LocalDateTime.parse(eventDto.getEndDate(), formatter));
        event.setLocation(eventDto.getLocation());
        event.setCategory(eventDto.getCategory());
        event.setAllDay(eventDto.isAllDay());
        event.setColor(eventDto.getColor());
        
        return event;
    }
}
