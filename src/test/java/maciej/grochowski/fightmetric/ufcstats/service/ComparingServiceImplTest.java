package maciej.grochowski.fightmetric.ufcstats.service;

import maciej.grochowski.fightmetric.ufcstats.dto.Advantage;
import maciej.grochowski.fightmetric.ufcstats.dto.FighterDTO;
import maciej.grochowski.fightmetric.ufcstats.enums.ESize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ComparingServiceImplTest {

    private static FighterDTO bestFighter;
    private static FighterDTO goodFighter;
    private static FighterDTO mediumFighter;
    private static FighterDTO weakFighter;

    @InjectMocks
    private ComparingServiceImpl comparingService;

    @BeforeEach
    void prepareEvents() {
        LocalDate date1 = LocalDate.of(2000, 5, 25);
        LocalDate date2 = LocalDate.of(1998, 4, 20);
        LocalDate date3 = LocalDate.of(1994, 3, 15);
        LocalDate date4 = LocalDate.of(1990, 4, 10);

        bestFighter = new FighterDTO("Ngannou", valueOf(198), valueOf(200), valueOf(5.5), valueOf(60),
                valueOf(2.2), valueOf(70), valueOf(2.5), valueOf(80), valueOf(60), date1);

        goodFighter = new FighterDTO("Rakic", valueOf(192), valueOf(194), valueOf(5), valueOf(53),
                valueOf(3.2), valueOf(65), valueOf(1.8), valueOf(70), valueOf(55), date2);

        mediumFighter = new FighterDTO("Whittaker", valueOf(186), valueOf(188), valueOf(4.4), valueOf(46),
                valueOf(4.0), valueOf(60), valueOf(1.2), valueOf(60), valueOf(47), date3);

        weakFighter = new FighterDTO("Edwards", valueOf(176), valueOf(170), valueOf(3.25), valueOf(30),
                valueOf(5.5), valueOf(54), valueOf(0.8), valueOf(55), valueOf(38), date4);
    }

    @Test
    void setFightersAdvantages_best_fighter_should_have_no_advantages_over_great_fighter() {
        // given + when
        comparingService.setFightersAdvantages(bestFighter, goodFighter);
        List<Advantage> advantages = bestFighter.getAdvantages();

        // then
        assertEquals(0, advantages.size());
    }

    @Test
    void setFightersAdvantages_best_fighter_should_have_10_slight_advantages_over_medium_fighter() {
        // given + when
        comparingService.setFightersAdvantages(bestFighter, mediumFighter);
        List<Advantage> advantages = bestFighter.getAdvantages();

        // then
        assertAll(
                () -> assertEquals(10, advantages.size()),
                () -> assertEquals(10, (int) advantages.stream().filter(adv -> adv.getESize() == ESize.SLIGHT).count())
        );
    }

    @Test
    void setFightersAdvantages_best_fighter_should_have_10_significant_advantages_over_weak_fighter() {
        // given + when
        comparingService.setFightersAdvantages(bestFighter, weakFighter);
        List<Advantage> advantages = bestFighter.getAdvantages();

        // then
        assertAll(
                () -> assertEquals(10, advantages.size()),
                () -> assertEquals(10, (int) advantages.stream().filter(adv -> adv.getESize() == ESize.SIGNIFICANT).count())
        );
    }

    @ParameterizedTest
    @MethodSource("fightersToCompareWithWeak")
    void setFightersAdvantages_weak_fighter_should_have_no_advantages_over_other_fighters(FighterDTO fighter) {
        // given + when
        comparingService.setFightersAdvantages(weakFighter, fighter);
        List<Advantage> advantages = weakFighter.getAdvantages();

        // then
        assertAll(
                () -> assertEquals(0, advantages.size())
        );
    }

    private static Stream<FighterDTO> fightersToCompareWithWeak() {
        return Stream.of(mediumFighter, goodFighter, bestFighter);
    }

    @Test
    void calculateAgeDifference_should_return_full_years_difference() {
        // given + when
        Integer ageDifference25Months = comparingService.calculateAgeDifference(bestFighter, goodFighter);
        Integer ageDifference47Months = comparingService.calculateAgeDifference(mediumFighter, weakFighter);

        // then
        assertAll(
                () -> assertEquals(ageDifference25Months, 2),
                () -> assertEquals(ageDifference47Months, 3)
        );
    }
}