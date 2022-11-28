package maciej.grochowski.fightmetric.ufcstats.enums;

public enum EAttribute {

    HEIGHT("Height"),
    REACH("Reach"),
    AGE("DOB"),
    STRIKES_LANDED_PER_MIN("Strikes Landed per Min. (SLpM)"),
    STRIKING_ACCURACY("Striking Accuracy"),
    STRIKES_ABSORBED_PER_MIN("Strikes Absorbed per Min. (SApM)"),
    STRIKING_DEFENSE("Defense"),
    TAKEDOWNS_AVERAGE("Takedowns Average/15 min."),
    TAKEDOWN_ACCURACY("Takedown Accuracy"),
    TAKEDOWN_DEFENSE("Takedown Defense");

    private String value;

    EAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
