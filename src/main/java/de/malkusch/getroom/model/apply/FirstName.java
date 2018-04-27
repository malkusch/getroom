package de.malkusch.getroom.model.apply;

import static java.util.Objects.requireNonNull;

public final class FirstName {

    private final String value;

    public FirstName(String firstName) {
        requireNonNull(firstName);
        if (firstName.isEmpty()) {
            throw new IllegalArgumentException("First name must not be empty");
        }
        this.value = firstName;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FirstName) {
            FirstName other = (FirstName) obj;
            return other.value.equals(value);

        } else {
            return false;
        }
    }

}
