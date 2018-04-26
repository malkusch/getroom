package de.malkusch.getroom.model;

public final class Price {

    private final int value;

    public Price(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.value = price;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Price) {
            Price other = (Price) obj;
            return other.value == value;

        } else {
            return false;
        }
    }

}
