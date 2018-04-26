package de.malkusch.getroom.model;

public final class City {

    private final int id;

    public City(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("City id must be positive");
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof City) {
            City other = (City) obj;
            return other.id == id;

        } else {
            return false;
        }
    }
}
