package maciej.grochowski.fightmetric.pinnacle.service;

import maciej.grochowski.fightmetric.pinnacle.entity.EventDB;
import maciej.grochowski.fightmetric.pinnacle.exception.TooManyRequestsException;
import maciej.grochowski.fightmetric.pinnacle.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventDB> getAllEvents() {
        return eventRepository.findAll();
    }
}
