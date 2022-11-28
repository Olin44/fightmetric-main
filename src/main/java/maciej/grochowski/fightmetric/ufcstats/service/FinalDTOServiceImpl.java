package maciej.grochowski.fightmetric.ufcstats.service;

import maciej.grochowski.fightmetric.pinnacle.entity.EventDB;
import maciej.grochowski.fightmetric.pinnacle.exception.TooManyRequestsException;
import maciej.grochowski.fightmetric.pinnacle.service.EventService;
import maciej.grochowski.fightmetric.ufcstats.dto.Advantage;
import maciej.grochowski.fightmetric.ufcstats.dto.FighterDTO;
import maciej.grochowski.fightmetric.ufcstats.dto.FinalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static maciej.grochowski.fightmetric.ufcstats.enums.ESize.SIGNIFICANT;
import static maciej.grochowski.fightmetric.ufcstats.enums.ESize.SLIGHT;

@Service
public class FinalDTOServiceImpl implements FinalDTOService{

    private final EventService eventService;
    private final ComparingService comparingService;
    private List<EventDB> eventsList;
    private final static Logger log = LoggerFactory.getLogger(FinalDTOServiceImpl.class);

    public FinalDTOServiceImpl(EventService eventService, ComparingService comparingService) {
        this.eventService = eventService;
        this.comparingService = comparingService;
    }

    @Override
    public void loadEvents() {
        eventsList = eventService.getAllEvents();
    }

    @Override
    public FinalDTO getFinalDTO(List<FighterDTO> fighters) {
        FighterDTO fighter1 = fighters.get(0);
        FighterDTO fighter2 = fighters.get(1);
        comparingService.setFightersAdvantages(fighter1, fighter2);

        FinalDTO finalDTO = new FinalDTO(
                fighter1.getName(),
                fighter1.getAdvantages(),
                fighter2.getName(),
                fighter2.getAdvantages()
        );

        for (EventDB event : eventsList) {
            if (Objects.equals(event.getFighter1(), fighter1.getName())) {
                finalDTO.setOdds1(event.getOdds1());
                finalDTO.setOdds2(event.getOdds2());
            } else if (Objects.equals(event.getFighter1(), fighter2.getName())) {
                finalDTO.setOdds1(event.getOdds2());
                finalDTO.setOdds2(event.getOdds1());
            }
        }

        return finalDTO;
    }

    @Override
    public void displayAdvantages(List<FighterDTO> fighters, String link) {
        FighterDTO fighter1 = fighters.get(0);
        FighterDTO fighter2 = fighters.get(1);

        List<Advantage> advantages = comparingService.getFightersAdvantages(fighter1, fighter2);
        if (advantages.isEmpty()) {
            return;
        }

        List<Advantage> significantAdvantages1 = comparingService.getAdvantagesFromFighter(fighter1, SIGNIFICANT);
        List<Advantage> slightAdvantages1 = comparingService.getAdvantagesFromFighter(fighter1, SLIGHT);
        List<Advantage> significantAdvantages2 = comparingService.getAdvantagesFromFighter(fighter2, SIGNIFICANT);
        List<Advantage> slightAdvantages2 = comparingService.getAdvantagesFromFighter(fighter2, SLIGHT);

        String name1 = fighter1.getName();
        String name2 = fighter2.getName();
        System.out.printf("%s vs %s %s %n", name1.toUpperCase(), name2.toUpperCase(), link);

        if (!significantAdvantages1.isEmpty()) {
            System.out.printf("%s has %d significant advantages in this matchup: %n", name1, significantAdvantages1.size());
            significantAdvantages1.forEach(System.out::println);
            System.out.println();
        }

        if (!slightAdvantages1.isEmpty()) {
            System.out.printf("%s has %d slight advantages in this matchup: %n", name1, slightAdvantages1.size());
            slightAdvantages1.forEach(System.out::println);
            System.out.println();
        }

        if (!significantAdvantages2.isEmpty()) {
            System.out.printf("%s has %d significant advantages in this matchup: %n", name2, significantAdvantages2.size());
            significantAdvantages2.forEach(System.out::println);
            System.out.println();
        }

        if (!slightAdvantages2.isEmpty()) {
            System.out.printf("%s has %d slight advantages in this matchup: %n", name2, slightAdvantages2.size());
            slightAdvantages2.forEach(System.out::println);
            System.out.println();
        }

        eventsList.stream()
                .filter(event -> event.toString().contains(name1))
                .findFirst()
                .ifPresent(event -> {
                    System.out.println(event.toString().toUpperCase());
                    System.out.println();
                    System.out.println("---");
                    System.out.println();
                });
    }

    @Override
    public List<EventDB> getEventsList() {
        if (eventsList.isEmpty()) {
            throw new TooManyRequestsException();
        }
        return eventsList;
    }
}
