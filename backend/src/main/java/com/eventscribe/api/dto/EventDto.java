
package com.eventscribe.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String location;
    private String category;
    private boolean isAllDay;
    private String color;
}
