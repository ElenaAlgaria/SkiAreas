package model;

import java.util.Objects;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class SkiRegion {
    private final String name;

    public SkiRegion(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SkiRegion skiRegion = (SkiRegion) o;
        return Objects.equals(name, skiRegion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}


