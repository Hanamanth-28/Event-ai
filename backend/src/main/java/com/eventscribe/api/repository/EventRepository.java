
package com.eventscribe.api.repository;

import com.eventscribe.api.model.Event;
import com.eventscribe.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByCreatedBy(User user);
}
