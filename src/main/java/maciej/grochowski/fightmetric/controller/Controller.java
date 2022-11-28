package maciej.grochowski.fightmetric.controller;

import maciej.grochowski.fightmetric.pinnacle.client.FightsProvider;
import maciej.grochowski.fightmetric.pinnacle.entity.EventDB;
import maciej.grochowski.fightmetric.pinnacle.exception.TooManyRequestsException;
import maciej.grochowski.fightmetric.pinnacle.service.EventService;
import maciej.grochowski.fightmetric.ufcstats.dto.FighterDTO;
import maciej.grochowski.fightmetric.ufcstats.dto.FinalDTO;
import maciej.grochowski.fightmetric.ufcstats.service.FinalDTOService;
import maciej.grochowski.fightmetric.ufcstats.service.ReadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class Controller {

    private final FinalDTOService finalDTOService;
    private final ReadingService readingService;

    public Controller(FinalDTOService finalDTOService, ReadingService readingService) {
        this.finalDTOService = finalDTOService;
        this.readingService = readingService;
    }

    @GetMapping("/odds")
    private List<EventDB> getAllEvents() {
        return finalDTOService.getEventsList();
    }

    @GetMapping("/finals")
    private List<FinalDTO> getAllFinals() {
        return readingService.getFinalDTOList();
    }

    @GetMapping("/fights")
    private List<List<FighterDTO>> getAllFights() {
        return readingService.getFightersList();
    }
}
