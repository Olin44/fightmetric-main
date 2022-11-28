package maciej.grochowski.fightmetric.ufcstats.enums;

public enum ESize {
    SLIGHT("slight"),
    SIGNIFICANT("significant");

    private String name;

    ESize(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
