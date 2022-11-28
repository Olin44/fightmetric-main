package maciej.grochowski.fightmetric.pinnacle.mapper;

import maciej.grochowski.fightmetric.pinnacle.dto.EventDTO;
import maciej.grochowski.fightmetric.pinnacle.dto.MoneyLine;
import maciej.grochowski.fightmetric.pinnacle.dto.Number;
import maciej.grochowski.fightmetric.pinnacle.dto.Periods;
import maciej.grochowski.fightmetric.pinnacle.entity.EventDB;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventMapperTest {

    private EventMapper eventMapper = Mappers.getMapper(EventMapper.class);

    @Test
    void eventDTOToEventDB_maps_correctly() {
        //given
        EventDTO eventDTO = new EventDTO();
        eventDTO.setFighter1("Fabricio Werdum");
        eventDTO.setFighter2("Cain Velasquez");

        Periods periods = new Periods();
        eventDTO.setPeriods(periods);

        Number number = new Number();
        periods.setNumber(number);

        MoneyLine moneyline = new MoneyLine();
        moneyline.setHome(1.80);
        moneyline.setAway(2.00);
        number.setMoneyLine(moneyline);

        // when
        EventDB mappedEvent = eventMapper.dtoToEventDB(eventDTO);

        // then
        assertAll(
                () -> assertEquals(mappedEvent.getFighter1(), eventDTO.getFighter1()),
                () -> assertEquals(mappedEvent.getFighter2(), eventDTO.getFighter2()),
                () -> assertEquals(mappedEvent.getOdds1().doubleValue(), moneyline.getHome()),
                () -> assertEquals(mappedEvent.getOdds2().doubleValue(), moneyline.getAway())
        );
    }
}