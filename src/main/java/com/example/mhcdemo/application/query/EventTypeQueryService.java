package com.example.mhcdemo.application.query;

import com.example.mhcdemo.repository.EventRepository;
import com.example.mhcdemo.repository.EventTypeRepository;
import com.example.mhcdemo.repository.entity.EventType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeQueryService {

    EventTypeRepository eventTypeRepository;

    public EventTypeQueryService(EventTypeRepository eventRepository) {
        this.eventTypeRepository = eventRepository;
    }

    public List<String> queryAllEventTypes() {
        return eventTypeRepository.findAllEventTypes();
    }

    public EventType queryEventIdByEventType(String eventType) {
        return eventTypeRepository.findFirstByType(eventType);
    }
}
