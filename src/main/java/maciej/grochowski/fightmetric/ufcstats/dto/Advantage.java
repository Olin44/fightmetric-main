package maciej.grochowski.fightmetric.ufcstats.dto;

import maciej.grochowski.fightmetric.ufcstats.enums.EAttribute;
import maciej.grochowski.fightmetric.ufcstats.enums.ESize;

import java.util.Objects;

public class Advantage {

    private EAttribute eAttribute;
    private ESize ESize;
    private String value;

    public Advantage() {
    }

    public Advantage(EAttribute eAttribute, ESize ESize, String value) {
        this.eAttribute = eAttribute;
        this.ESize = ESize;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Advantage{" +
                "eAttribute=" + eAttribute +
                ", ESize=" + ESize +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advantage advantage = (Advantage) o;
        return eAttribute == advantage.eAttribute && ESize == advantage.ESize && value.equals(advantage.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eAttribute, ESize, value);
    }

    public ESize getESize() {
        return ESize;
    }

    public EAttribute geteAttribute() {
        return eAttribute;
    }

    public String getValue() {
        return value;
    }
}
