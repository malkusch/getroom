package de.malkusch.getroom.model.apply;

import static java.util.Objects.requireNonNull;

public final class Phone {
    private final String value;

    public Phone(String phone) {
        requireNonNull(phone);
        if (phone.isEmpty()) {
            throw new IllegalArgumentException("Phone must not be empty");
        }
        this.value = phone;
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
        if (obj instanceof Phone) {
            Phone other = (Phone) obj;
            return other.value.equals(value);

        } else {
            return false;
        }
    }

}
