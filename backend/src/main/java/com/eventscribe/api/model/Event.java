
package com.eventscribe.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    
    @Id
    private String id;
    
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String category;
    private boolean isAllDay;
    private String color;
    
    private User createdBy;
}
