package maciej.grochowski.fightmetric.pinnacle.mapper;

import maciej.grochowski.fightmetric.pinnacle.entity.EventDB;
import maciej.grochowski.fightmetric.pinnacle.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EventMapper {

    @Mapping(target = "odds1", source = "eventDTO.periods.number.moneyLine.home")
    @Mapping(target = "odds2", source = "eventDTO.periods.number.moneyLine.away")
    EventDB dtoToEventDB(EventDTO eventDTO);
}
