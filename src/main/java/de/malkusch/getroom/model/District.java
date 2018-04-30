package de.malkusch.getroom.model;

public final class District {

    private final int id;

    public District(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("District id must be positive");
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
        if (obj instanceof District) {
            District other = (District) obj;
            return other.id == id;

        } else {
            return false;
        }
    }
}
