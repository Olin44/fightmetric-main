package maciej.grochowski.fightmetric.pinnacle.service;

import maciej.grochowski.fightmetric.pinnacle.entity.EventDB;
import maciej.grochowski.fightmetric.pinnacle.exception.TooManyRequestsException;
import maciej.grochowski.fightmetric.pinnacle.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    private List<EventDB> eventsList;

    @BeforeEach
    void init() {
        eventsList = new ArrayList<>();
        eventsList.add(new EventDB("Junior dos Santos", "Stipe Miocic", BigDecimal.valueOf(1.40), BigDecimal.valueOf(3.10)));
        eventsList.add(new EventDB("Alistair Overeem", "Fabricio Werdum", BigDecimal.valueOf(1.70), BigDecimal.valueOf(2.20)));
        eventsList.add(new EventDB("Joanna Jedrzejczyk", "Carla Esparza", BigDecimal.valueOf(2.30), BigDecimal.valueOf(1.65)));
        eventsList.add(new EventDB("Dustin Poirier", "Conor McGregor", BigDecimal.valueOf(3.60), BigDecimal.valueOf(1.30)));

        given(eventRepository.findAll()).willReturn(eventsList);
    }

    @Test
    void getAllEvents_will_return_all_events() {
        // given + when
        int actualSize = eventService.getAllEvents().size();

        // then
        assertEquals(4, actualSize);
    }

    @Test
    void getAllEvents_empty_will_throw_TooManyRequestsException() {
        // given + when
        eventsList.clear();

        // then
        assertThrows(TooManyRequestsException.class, () -> eventService.getAllEvents());
    }
}