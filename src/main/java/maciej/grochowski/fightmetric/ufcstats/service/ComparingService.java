package maciej.grochowski.fightmetric.ufcstats.service;

import maciej.grochowski.fightmetric.ufcstats.dto.Advantage;
import maciej.grochowski.fightmetric.ufcstats.enums.ESize;
import maciej.grochowski.fightmetric.ufcstats.dto.FighterDTO;

import java.util.List;

public interface ComparingService {

    List<Advantage> getAdvantagesFromFighter(FighterDTO fighter, ESize eSize);

    void setFightersAdvantages(FighterDTO fighter1, FighterDTO fighter2);

    List<Advantage> getFightersAdvantages(FighterDTO fighter1, FighterDTO fighter2);
}
