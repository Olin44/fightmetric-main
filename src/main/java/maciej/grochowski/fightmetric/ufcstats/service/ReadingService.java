package maciej.grochowski.fightmetric.ufcstats.service;

import maciej.grochowski.fightmetric.ufcstats.dto.FighterDTO;
import maciej.grochowski.fightmetric.ufcstats.dto.FinalDTO;

import java.util.List;

public interface ReadingService {

    List<List<FighterDTO>> getFightersList();

    List<FinalDTO> getFinalDTOList();

    List<List<FighterDTO>> getAllFightersFromUFCStats();
}
