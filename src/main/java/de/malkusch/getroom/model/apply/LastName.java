package de.malkusch.getroom.model.apply;

import static java.util.Objects.requireNonNull;

public final class LastName {

    private final String value;

    public LastName(String lastName) {
        requireNonNull(lastName);
        if (lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name must not be empty");
        }
        this.value = lastName;
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
        if (obj instanceof LastName) {
            LastName other = (LastName) obj;
            return other.value.equals(value);

        } else {
            return false;
        }
    }

}
