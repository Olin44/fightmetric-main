package maciej.grochowski.fightmetric.ufcstats.service;

import maciej.grochowski.fightmetric.ufcstats.dto.Advantage;
import maciej.grochowski.fightmetric.ufcstats.dto.FighterDTO;
import maciej.grochowski.fightmetric.ufcstats.enums.ESize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.*;
import static maciej.grochowski.fightmetric.ufcstats.enums.ESize.SIGNIFICANT;
import static maciej.grochowski.fightmetric.ufcstats.enums.ESize.SLIGHT;

@Service
public class ComparingServiceImpl implements ComparingService {

    private static final Logger log = LoggerFactory.getLogger(ComparingServiceImpl.class);

    @Override
    public List<Advantage> getFightersAdvantages(FighterDTO fighter1, FighterDTO fighter2) {
        List<Advantage> advantages1 = new ArrayList<>();
        List<Advantage> advantages2 = new ArrayList<>();

        try {
            BigDecimal heightDiff = fighter1.getHeight().subtract(fighter2.getHeight());
            String hDefStr = heightDiff.abs().toString();
            if (heightDiff.compareTo(BigDecimal.valueOf(12)) > 0) {
                advantages1.add(new Advantage(HEIGHT, SIGNIFICANT, hDefStr));
            } else if (heightDiff.compareTo(BigDecimal.valueOf(6)) > 0) {
                advantages1.add(new Advantage(HEIGHT, SLIGHT, hDefStr));
            } else if (heightDiff.compareTo(BigDecimal.valueOf(-12)) < 0) {
                advantages2.add(new Advantage(HEIGHT, SIGNIFICANT, hDefStr));
            } else if (heightDiff.compareTo(BigDecimal.valueOf(-6)) < 0) {
                advantages2.add(new Advantage(HEIGHT, SLIGHT, hDefStr));
            }

            BigDecimal reachDiff = fighter1.getReach().subtract(fighter2.getReach());
            String rDiffStr = reachDiff.abs().toString();
            if (reachDiff.compareTo(BigDecimal.valueOf(15)) > 0) {
                advantages1.add(new Advantage(REACH, SIGNIFICANT, rDiffStr));
            } else if (reachDiff.compareTo(BigDecimal.valueOf(7)) > 0) {
                advantages1.add(new Advantage(REACH, SLIGHT, rDiffStr));
            } else if (reachDiff.compareTo(BigDecimal.valueOf(-15)) < 0) {
                advantages2.add(new Advantage(REACH, SIGNIFICANT, rDiffStr));
            } else if (reachDiff.compareTo(BigDecimal.valueOf(-7)) < 0) {
                advantages2.add(new Advantage(REACH, SLIGHT, rDiffStr));
            }


            BigDecimal strikesLandedDiff = fighter1.getStrikesLanded().subtract(fighter2.getStrikesLanded());
            String sLandedDiffStr = strikesLandedDiff.abs().toString();
            if (strikesLandedDiff.compareTo(BigDecimal.valueOf(2.5)) > 0) {
                advantages1.add(new Advantage(STRIKES_LANDED_PER_MIN, SIGNIFICANT, sLandedDiffStr));
            } else if (strikesLandedDiff.compareTo(BigDecimal.valueOf(1.25)) > 0) {
                advantages1.add(new Advantage(STRIKES_LANDED_PER_MIN, SLIGHT, sLandedDiffStr));
            } else if (strikesLandedDiff.compareTo(BigDecimal.valueOf(-2.5)) < 0) {
                advantages2.add(new Advantage(STRIKES_LANDED_PER_MIN, SIGNIFICANT, sLandedDiffStr));
            } else if (strikesLandedDiff.compareTo(BigDecimal.valueOf(-1.25)) < 0) {
                advantages2.add(new Advantage(STRIKES_LANDED_PER_MIN, SLIGHT, sLandedDiffStr));
            }

            BigDecimal strikesAbsDiff = fighter1.getStrikesAbsorbed().subtract(fighter2.getStrikesAbsorbed());
            String sAbsDiffStr = strikesAbsDiff.abs().toString();
            if (strikesAbsDiff.compareTo(BigDecimal.valueOf(2.5)) > 0) {
                advantages2.add(new Advantage(STRIKES_ABSORBED_PER_MIN, SIGNIFICANT, sAbsDiffStr));
            } else if (strikesAbsDiff.compareTo(BigDecimal.valueOf(1.25)) > 0) {
                advantages2.add(new Advantage(STRIKES_ABSORBED_PER_MIN, SLIGHT, sAbsDiffStr));
            } else if (strikesAbsDiff.compareTo(BigDecimal.valueOf(-2.5)) < 0) {
                advantages1.add(new Advantage(STRIKES_ABSORBED_PER_MIN, SIGNIFICANT, sAbsDiffStr));
            } else if (strikesAbsDiff.compareTo(BigDecimal.valueOf(-1.25)) < 0) {
                advantages1.add(new Advantage(STRIKES_ABSORBED_PER_MIN, SLIGHT, sAbsDiffStr));
            }

            BigDecimal strikingAccDiff = fighter1.getStrikingAccuracy().subtract(fighter2.getStrikingAccuracy());
            String sAccDiffStr = getPercentageDifference(strikingAccDiff);
            if (strikingAccDiff.compareTo(BigDecimal.valueOf(0.15)) > 0) {
                advantages1.add(new Advantage(STRIKING_ACCURACY, SIGNIFICANT, sAccDiffStr));
            } else if (strikingAccDiff.compareTo(BigDecimal.valueOf(0.075)) > 0) {
                advantages1.add(new Advantage(STRIKING_ACCURACY, SLIGHT, sAccDiffStr));
            } else if (strikingAccDiff.compareTo(BigDecimal.valueOf(-0.15)) < 0) {
                advantages2.add(new Advantage(STRIKING_ACCURACY, SIGNIFICANT, sAccDiffStr));
            } else if (strikingAccDiff.compareTo(BigDecimal.valueOf(-0.075)) < 0) {
                advantages2.add(new Advantage(STRIKING_ACCURACY, SLIGHT, sAccDiffStr));
            }

            BigDecimal strikesDefenceDiff = fighter1.getStrikesDefence().subtract(fighter2.getStrikesDefence());
            String sDefDiffStr = getPercentageDifference(strikesDefenceDiff);
            if (strikesDefenceDiff.compareTo(BigDecimal.valueOf(0.15)) > 0) {
                advantages1.add(new Advantage(STRIKING_DEFENSE, SIGNIFICANT, sDefDiffStr));
            } else if (strikesDefenceDiff.compareTo(BigDecimal.valueOf(0.075)) > 0) {
                advantages1.add(new Advantage(STRIKING_DEFENSE, SLIGHT, sDefDiffStr));
            } else if (strikesDefenceDiff.compareTo(BigDecimal.valueOf(-0.15)) < 0) {
                advantages2.add(new Advantage(STRIKING_DEFENSE, SIGNIFICANT, sDefDiffStr));
            } else if (strikesDefenceDiff.compareTo(BigDecimal.valueOf(-0.075)) < 0) {
                advantages2.add(new Advantage(STRIKING_DEFENSE, SLIGHT, sDefDiffStr));
            }

            BigDecimal takedownsAverage = fighter1.getTakedownsAverage().subtract(fighter2.getTakedownsAccuracy());
            String sTdAdvDiff = takedownsAverage.abs().toString();
            if (takedownsAverage.compareTo(BigDecimal.valueOf(2)) > 0) {
                advantages1.add(new Advantage(TAKEDOWNS_AVERAGE, SIGNIFICANT, sTdAdvDiff));
            } else if (takedownsAverage.compareTo(BigDecimal.valueOf(1)) > 0) {
                advantages1.add(new Advantage(TAKEDOWNS_AVERAGE, SLIGHT, sTdAdvDiff));
            } else if (takedownsAverage.compareTo(BigDecimal.valueOf(-2)) < 0) {
                advantages2.add(new Advantage(TAKEDOWNS_AVERAGE, SIGNIFICANT, sTdAdvDiff));
            } else if (takedownsAverage.compareTo(BigDecimal.valueOf(-1)) < 0) {
                advantages2.add(new Advantage(TAKEDOWNS_AVERAGE, SLIGHT, sTdAdvDiff));
            }

            BigDecimal takedownsAccuaryDiff = fighter1.getTakedownsAccuracy().subtract(fighter2.getTakedownsAccuracy());
            String sTdAccDiff = getPercentageDifference(takedownsAccuaryDiff);
            if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(0.2)) > 0) {
                advantages1.add(new Advantage(TAKEDOWN_ACCURACY, SIGNIFICANT, sTdAccDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(0.1)) > 0) {
                advantages1.add(new Advantage(TAKEDOWN_ACCURACY, SLIGHT, sTdAccDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(-0.2)) < 0) {
                advantages2.add(new Advantage(TAKEDOWN_ACCURACY, SIGNIFICANT, sTdAccDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(-0.1)) < 0) {
                advantages2.add(new Advantage(TAKEDOWN_ACCURACY, SLIGHT, sTdAccDiff));
            }

            BigDecimal takedownsDefendedDiff = fighter1.getTakedownsDefence().subtract(fighter2.getTakedownsDefence());
            String sTdDefDiff = getPercentageDifference(takedownsDefendedDiff);
            if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(0.2)) > 0) {
                advantages1.add(new Advantage(TAKEDOWN_DEFENSE, SIGNIFICANT, sTdDefDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(0.1)) > 0) {
                advantages1.add(new Advantage(TAKEDOWN_DEFENSE, SLIGHT, sTdDefDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(-0.2)) < 0) {
                advantages2.add(new Advantage(TAKEDOWN_DEFENSE, SIGNIFICANT, sTdDefDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(-0.1)) < 0) {
                advantages2.add(new Advantage(TAKEDOWN_DEFENSE, SLIGHT, sTdDefDiff));
            }

            Integer ageDifference = calculateAgeDifference(fighter1, fighter2);
            String sAgeDiff = String.valueOf(ageDifference);
            if (ageDifference > 8) {
                advantages2.add(new Advantage(AGE, SIGNIFICANT, sAgeDiff));
            } else if (ageDifference > 4) {
                advantages2.add(new Advantage(AGE, SLIGHT, sAgeDiff));
            } else if (ageDifference < -8) {
                advantages1.add(new Advantage(AGE, SIGNIFICANT, sAgeDiff));
            } else if (ageDifference < -4) {
                advantages1.add(new Advantage(AGE, SLIGHT, sAgeDiff));
            }

            if (advantages1.isEmpty() && advantages2.isEmpty()) {
                return new ArrayList<>();
            }


        } catch (NullPointerException e) {
            log.error("Missing data - calucations incomplete for {} vs {}", fighter1.getName(), fighter2.getName());
            return new ArrayList<>();
        }

        fighter1.setAdvantages(advantages1);
        fighter2.setAdvantages(advantages2);

        return List.of(new Advantage());
    }

    @Override
    public void setFightersAdvantages(FighterDTO fighter1, FighterDTO fighter2) {
        List<Advantage> advantages1 = new ArrayList<>();
        List<Advantage> advantages2 = new ArrayList<>();

        try {
            BigDecimal heightDiff = fighter1.getHeight().subtract(fighter2.getHeight());
            String hDefStr = heightDiff.abs().toString();
            if (heightDiff.compareTo(BigDecimal.valueOf(12)) > 0) {
                advantages1.add(new Advantage(HEIGHT, SIGNIFICANT, hDefStr));
            } else if (heightDiff.compareTo(BigDecimal.valueOf(6)) > 0) {
                advantages1.add(new Advantage(HEIGHT, SLIGHT, hDefStr));
            } else if (heightDiff.compareTo(BigDecimal.valueOf(-12)) < 0) {
                advantages2.add(new Advantage(HEIGHT, SIGNIFICANT, hDefStr));
            } else if (heightDiff.compareTo(BigDecimal.valueOf(-6)) < 0) {
                advantages2.add(new Advantage(HEIGHT, SLIGHT, hDefStr));
            }

            BigDecimal reachDiff = fighter1.getReach().subtract(fighter2.getReach());
            String rDiffStr = reachDiff.abs().toString();
            if (reachDiff.compareTo(BigDecimal.valueOf(12)) > 0) {
                advantages1.add(new Advantage(REACH, SIGNIFICANT, rDiffStr));
            } else if (reachDiff.compareTo(BigDecimal.valueOf(6)) > 0) {
                advantages1.add(new Advantage(REACH, SLIGHT, rDiffStr));
            } else if (reachDiff.compareTo(BigDecimal.valueOf(-12)) < 0) {
                advantages2.add(new Advantage(REACH, SIGNIFICANT, rDiffStr));
            } else if (reachDiff.compareTo(BigDecimal.valueOf(-6)) < 0) {
                advantages2.add(new Advantage(REACH, SLIGHT, rDiffStr));
            }

            BigDecimal strikesLandedDiff = fighter1.getStrikesLanded().subtract(fighter2.getStrikesLanded());
            String sLandedDiffStr = strikesLandedDiff.abs().toString();
            if (strikesLandedDiff.compareTo(BigDecimal.valueOf(2)) > 0) {
                advantages1.add(new Advantage(STRIKES_LANDED_PER_MIN, SIGNIFICANT, sLandedDiffStr));
            } else if (strikesLandedDiff.compareTo(BigDecimal.valueOf(1)) > 0) {
                advantages1.add(new Advantage(STRIKES_LANDED_PER_MIN, SLIGHT, sLandedDiffStr));
            } else if (strikesLandedDiff.compareTo(BigDecimal.valueOf(-2)) < 0) {
                advantages2.add(new Advantage(STRIKES_LANDED_PER_MIN, SIGNIFICANT, sLandedDiffStr));
            } else if (strikesLandedDiff.compareTo(BigDecimal.valueOf(-1)) < 0) {
                advantages2.add(new Advantage(STRIKES_LANDED_PER_MIN, SLIGHT, sLandedDiffStr));
            }

            BigDecimal strikingAccDiff = fighter1.getStrikingAccuracy().subtract(fighter2.getStrikingAccuracy());
            String sAccDiffStr = getPercentageDifference(strikingAccDiff);
            if (strikingAccDiff.compareTo(BigDecimal.valueOf(15)) > 0) {
                advantages1.add(new Advantage(STRIKING_ACCURACY, SIGNIFICANT, sAccDiffStr));
            } else if (strikingAccDiff.compareTo(BigDecimal.valueOf(7.5)) > 0) {
                advantages1.add(new Advantage(STRIKING_ACCURACY, SLIGHT, sAccDiffStr));
            } else if (strikingAccDiff.compareTo(BigDecimal.valueOf(-15)) < 0) {
                advantages2.add(new Advantage(STRIKING_ACCURACY, SIGNIFICANT, sAccDiffStr));
            } else if (strikingAccDiff.compareTo(BigDecimal.valueOf(-7.5)) < 0) {
                advantages2.add(new Advantage(STRIKING_ACCURACY, SLIGHT, sAccDiffStr));
            }

            BigDecimal strikesAbsDiff = fighter1.getStrikesAbsorbed().subtract(fighter2.getStrikesAbsorbed());
            String sAbsDiffStr = strikesAbsDiff.abs().toString();
            if (strikesAbsDiff.compareTo(BigDecimal.valueOf(2)) > 0) {
                advantages2.add(new Advantage(STRIKES_ABSORBED_PER_MIN, SIGNIFICANT, sAbsDiffStr));
            } else if (strikesAbsDiff.compareTo(BigDecimal.valueOf(1)) > 0) {
                advantages2.add(new Advantage(STRIKES_ABSORBED_PER_MIN, SLIGHT, sAbsDiffStr));
            } else if (strikesAbsDiff.compareTo(BigDecimal.valueOf(-2)) < 0) {
                advantages1.add(new Advantage(STRIKES_ABSORBED_PER_MIN, SIGNIFICANT, sAbsDiffStr));
            } else if (strikesAbsDiff.compareTo(BigDecimal.valueOf(-1)) < 0) {
                advantages1.add(new Advantage(STRIKES_ABSORBED_PER_MIN, SLIGHT, sAbsDiffStr));
            }

            BigDecimal strikesDefenceDiff = fighter1.getStrikesDefence().subtract(fighter2.getStrikesDefence());
            String sDefDiffStr = getPercentageDifference(strikesDefenceDiff);
            if (strikesDefenceDiff.compareTo(BigDecimal.valueOf(15)) > 0) {
                advantages1.add(new Advantage(STRIKING_DEFENSE, SIGNIFICANT, sDefDiffStr));
            } else if (strikesDefenceDiff.compareTo(BigDecimal.valueOf(7.5)) > 0) {
                advantages1.add(new Advantage(STRIKING_DEFENSE, SLIGHT, sDefDiffStr));
            } else if (strikesDefenceDiff.compareTo(BigDecimal.valueOf(-15)) < 0) {
                advantages2.add(new Advantage(STRIKING_DEFENSE, SIGNIFICANT, sDefDiffStr));
            } else if (strikesDefenceDiff.compareTo(BigDecimal.valueOf(-7.5)) < 0) {
                advantages2.add(new Advantage(STRIKING_DEFENSE, SLIGHT, sDefDiffStr));
            }

            BigDecimal takedownsAverage = fighter1.getTakedownsAverage().subtract(fighter2.getTakedownsAverage());
            String sTdAdvDiff = takedownsAverage.abs().toString();
            if (takedownsAverage.compareTo(BigDecimal.valueOf(1.5)) > 0) {
                advantages1.add(new Advantage(TAKEDOWNS_AVERAGE, SIGNIFICANT, sTdAdvDiff));
            } else if (takedownsAverage.compareTo(BigDecimal.valueOf(0.75)) > 0) {
                advantages1.add(new Advantage(TAKEDOWNS_AVERAGE, SLIGHT, sTdAdvDiff));
            } else if (takedownsAverage.compareTo(BigDecimal.valueOf(-1.5)) < 0) {
                advantages2.add(new Advantage(TAKEDOWNS_AVERAGE, SIGNIFICANT, sTdAdvDiff));
            } else if (takedownsAverage.compareTo(BigDecimal.valueOf(-0.75)) < 0) {
                advantages2.add(new Advantage(TAKEDOWNS_AVERAGE, SLIGHT, sTdAdvDiff));
            }

            BigDecimal takedownsAccuaryDiff = fighter1.getTakedownsAccuracy().subtract(fighter2.getTakedownsAccuracy());
            String sTdAccDiff = getPercentageDifference(takedownsAccuaryDiff);
            if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(20)) > 0) {
                advantages1.add(new Advantage(TAKEDOWN_ACCURACY, SIGNIFICANT, sTdAccDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(10)) > 0) {
                advantages1.add(new Advantage(TAKEDOWN_ACCURACY, SLIGHT, sTdAccDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(-20)) < 0) {
                advantages2.add(new Advantage(TAKEDOWN_ACCURACY, SIGNIFICANT, sTdAccDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(-10)) < 0) {
                advantages2.add(new Advantage(TAKEDOWN_ACCURACY, SLIGHT, sTdAccDiff));
            }

            BigDecimal takedownsDefendedDiff = fighter1.getTakedownsDefence().subtract(fighter2.getTakedownsDefence());
            String sTdDefDiff = getPercentageDifference(takedownsDefendedDiff);
            if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(20)) > 0) {
                advantages1.add(new Advantage(TAKEDOWN_DEFENSE, SIGNIFICANT, sTdDefDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(10)) > 0) {
                advantages1.add(new Advantage(TAKEDOWN_DEFENSE, SLIGHT, sTdDefDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(-20)) < 0) {
                advantages2.add(new Advantage(TAKEDOWN_DEFENSE, SIGNIFICANT, sTdDefDiff));
            } else if (takedownsAccuaryDiff.compareTo(BigDecimal.valueOf(-10)) < 0) {
                advantages2.add(new Advantage(TAKEDOWN_DEFENSE, SLIGHT, sTdDefDiff));
            }

            Integer ageDifference = calculateAgeDifference(fighter1, fighter2);
            String sAgeDiff = String.valueOf(ageDifference);
            if (ageDifference > 8) {
                advantages2.add(new Advantage(AGE, SIGNIFICANT, sAgeDiff));
            } else if (ageDifference > 4) {
                advantages2.add(new Advantage(AGE, SLIGHT, sAgeDiff));
            } else if (ageDifference < -8) {
                advantages1.add(new Advantage(AGE, SIGNIFICANT, sAgeDiff));
            } else if (ageDifference < -4) {
                advantages1.add(new Advantage(AGE, SLIGHT, sAgeDiff));
            }

        } catch (NullPointerException e) {
            log.error("Missing data - calucations incomplete for {} vs {}", fighter1.getName(), fighter2.getName());
            return;
        }

        fighter1.setAdvantages(advantages1);
        fighter2.setAdvantages(advantages2);
    }

    @Override
    public List<Advantage> getAdvantagesFromFighter(FighterDTO fighter, ESize eSize) {
        return fighter.getAdvantages().stream()
                .filter(adv -> adv.getESize() == eSize)
                .collect(Collectors.toList());
    }

    private String getPercentageDifference(BigDecimal difference) {
        return difference.abs() + "%";
    }

    public Integer calculateAgeDifference(FighterDTO fighter1, FighterDTO fighter2) {
        LocalDate date1 = fighter1.getDob();
        LocalDate date2 = fighter2.getDob();

        return Math.abs(Period.between(date1, date2).getYears());
    }
}
