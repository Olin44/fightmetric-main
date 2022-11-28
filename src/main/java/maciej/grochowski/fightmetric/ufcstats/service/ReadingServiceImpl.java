package maciej.grochowski.fightmetric.ufcstats.service;

import maciej.grochowski.fightmetric.pinnacle.client.FightsProvider;
import maciej.grochowski.fightmetric.ufcstats.dto.FighterDTO;
import maciej.grochowski.fightmetric.ufcstats.dto.FinalDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.AGE;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.HEIGHT;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.REACH;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.STRIKES_ABSORBED_PER_MIN;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.STRIKES_LANDED_PER_MIN;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.STRIKING_ACCURACY;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.STRIKING_DEFENSE;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.TAKEDOWNS_AVERAGE;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.TAKEDOWN_ACCURACY;
import static maciej.grochowski.fightmetric.ufcstats.enums.EAttribute.TAKEDOWN_DEFENSE;

@Service
public class ReadingServiceImpl implements ReadingService {

    private final FinalDTOService finalDTOService;
    private final FightsProvider fightsProvider;

    public ReadingServiceImpl(FinalDTOService finalDTOService, FightsProvider fightsProvider) {
        this.finalDTOService = finalDTOService;
        this.fightsProvider = fightsProvider;
    }

    private final Logger log = LoggerFactory.getLogger(ReadingServiceImpl.class);

    private final int linkLength = 54;
    private final String nameText = "fight-details__person-link";
    private final String attributesText = "b-fight-details__table-text";
    private List<String> attributes;
    private List<List<FighterDTO>> fightersList;
    private List<FinalDTO> finalDTOList;
    private final BigDecimal footLength = BigDecimal.valueOf(30.48);
    private final BigDecimal inchLength = BigDecimal.valueOf(2.54);

    private String pattern = "MMMMM dd yyyy";
    private String mainURL = "http://www.ufcstats.com/statistics/events/upcoming";
    private String classOfLinks = "b-link b-link_style_black";

    @PostConstruct
    private void initData() {
        fightsProvider.loadFightsData();
        finalDTOService.loadEvents();
        fightersList = getAllFightersFromUFCStats();
        finalDTOList = fightersList.stream()
                .map(finalDTOService::getFinalDTO)
                .collect(Collectors.toList());

        log.info("Loading complete");
    }


    @Override
    public List<List<FighterDTO>> getFightersList() {
        return fightersList;
    }

    @Override
    public List<FinalDTO> getFinalDTOList() {
        return finalDTOList;
    }

    @Override
    public List<List<FighterDTO>> getAllFightersFromUFCStats() {
        log.info("Attempting to fetch data from UFCstats - it might take up some time...");
        List<List<FighterDTO>> list = new ArrayList<>();

        try {
            List<String> eventLinkList = getLinksFromURL(mainURL);
            eventLinkList.forEach(
                    eventURL -> {
                        try {
                            List<String> fights = getLinksFromURL(eventURL);
                            fights.forEach(fightURL -> {
                                try {
                                    list.add(getFightersListFromFightURL(fightURL));
                                } catch (IOException e) {
                                    log.error(e.getMessage(), e);
                                }
                            });
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    }
            );
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return list;
    }

    private List<String> getLinksFromURL(String linkFrom) throws IOException {
        Document doc = Jsoup.connect(linkFrom).get();
        return doc.getElementsByClass(classOfLinks)
                .stream()
                .filter(elem -> !elem.toString().contains("fighter-details"))
                .map(elem -> {
                    String line = elem.toString();
                    return line.substring(line.indexOf("http"), line.indexOf("http") + linkLength);
                })
                .collect(Collectors.toList());
    }

    private List<FighterDTO> getFightersListFromFightURL(String fightURL) throws IOException {
        FighterDTO fighter1 = new FighterDTO();
        FighterDTO fighter2 = new FighterDTO();

        BufferedReader reader = getReaderFromURL(fightURL);
        String line;

        attributes = new ArrayList<>();
        List<String> names = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if (line.contains(attributesText)) {
                attributes.add(getDataFromParagraph(line));
                if (line.contains(AGE.getValue())) {
                    do {
                        line = reader.readLine();
                    } while (!line.contains(","));
                    attributes.add(line.trim());

                    do {
                        line = reader.readLine();
                    } while (!line.contains(","));
                    attributes.add(line.trim());
                }
            } else if (line.contains(nameText)) {
                names.add(getDataFromParagraph(line).trim());
            }
        }

        fighter1.setName(names.get(0));
        fighter2.setName(names.get(1));

        int heightIndex = attributes.indexOf(HEIGHT.getValue());
        String fighter1Height = attributes.get(heightIndex + 1);
        String fighter2Height = attributes.get(heightIndex + 2);

        fighter1.setHeight(convertToCm(fighter1Height, false));
        fighter2.setHeight(convertToCm(fighter2Height, false));

        int reachIndex = attributes.indexOf(REACH.getValue());
        String fighter1Reach = attributes.get(reachIndex + 1);
        String fighter2Reach = attributes.get(reachIndex + 2);

        fighter1.setReach(convertToCm(fighter1Reach, true));
        fighter2.setReach(convertToCm(fighter2Reach, true));

        int dobIndex = attributes.indexOf(AGE.getValue());
        try {
            fighter1.setDob(getDateFromString(attributes.get(dobIndex + 1)));
            fighter2.setDob(getDateFromString(attributes.get(dobIndex + 2)));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }

        int slpmIndex = attributes.indexOf(STRIKES_LANDED_PER_MIN.getValue());
        fighter1.setStrikesLanded(getBigDecimalFromAttributesIndex(slpmIndex + 1));
        fighter2.setStrikesLanded(getBigDecimalFromAttributesIndex(slpmIndex + 2));

        int strAccIndex = attributes.indexOf(STRIKING_ACCURACY.getValue());
        fighter1.setStrikingAccuracy(getBigDecimalFromAttributesIndex(strAccIndex + 1));
        fighter2.setStrikingAccuracy(getBigDecimalFromAttributesIndex(strAccIndex + 2));

        int sapmIndex = attributes.indexOf(STRIKES_ABSORBED_PER_MIN.getValue());
        fighter1.setStrikesAbsorbed(getBigDecimalFromAttributesIndex(sapmIndex + 1));
        fighter2.setStrikesAbsorbed(getBigDecimalFromAttributesIndex(sapmIndex + 2));

        int strDefIndex = attributes.indexOf(STRIKING_DEFENSE.getValue());
        fighter1.setStrikesDefence(getBigDecimalFromAttributesIndex(strDefIndex + 1));
        fighter2.setStrikesDefence(getBigDecimalFromAttributesIndex(strDefIndex + 2));

        int tdAvgIndex = attributes.indexOf(TAKEDOWNS_AVERAGE.getValue());
        fighter1.setTakedownsAverage(getBigDecimalFromAttributesIndex(tdAvgIndex + 1));
        fighter2.setTakedownsAverage(getBigDecimalFromAttributesIndex(tdAvgIndex + 2));

        int tdaIndex = attributes.indexOf(TAKEDOWN_ACCURACY.getValue());

        fighter1.setTakedownsAccuracy(getBigDecimalFromAttributesIndex(tdaIndex + 1));
        fighter2.setTakedownsAccuracy(getBigDecimalFromAttributesIndex(tdaIndex + 2));

        int tddIndex = attributes.indexOf(TAKEDOWN_DEFENSE.getValue());
        fighter1.setTakedownsDefence(getBigDecimalFromAttributesIndex(tddIndex + 1));
        fighter2.setTakedownsDefence(getBigDecimalFromAttributesIndex(tddIndex + 2));

        return List.of(fighter1, fighter2);
    }

    private String getDataFromParagraph(String line) {
        try {
            return line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));
        } catch (IndexOutOfBoundsException e) {
            return line.substring(line.indexOf(">") + 1);
        }
    }

    private BigDecimal convertToCm(String measure, Boolean isReach) {
        try {
            BigDecimal feet = isReach ? BigDecimal.ZERO : BigDecimal.valueOf(Double.valueOf(measure.substring(0, measure.indexOf("'"))));
            BigDecimal inches = BigDecimal.valueOf(Double.valueOf(measure.substring(measure.indexOf(" ") + 1, measure.length() - 1)));
            return feet.multiply(footLength).add(inches.multiply(inchLength)).setScale(2, RoundingMode.UP);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate getDateFromString(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("us", "us"));
        return simpleDateFormat.parse(dateString.replace(",", "")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private BigDecimal getBigDecimalFromAttributesIndex(int attributeIndex) {
        String attribute = attributes.get(attributeIndex);
        String value;

        if (attribute.contains("%") && attribute.length() == 2) {
            value = attribute.substring(0, 1);
        } else if (attribute.contains("%")) {
            value = attribute.substring(0, 2);
        } else {
            value = attribute;
        }
        return BigDecimal.valueOf(Double.valueOf(value)).setScale(2, RoundingMode.UP);
    }

    private BufferedReader getReaderFromURL(String link) throws IOException {
        URL url = new URL(link);
        InputStream input = url.openStream();
        return new BufferedReader(new InputStreamReader(input));
    }
}
