package de.malkusch.getroom.model.apply;

import static java.util.Objects.requireNonNull;

public final class Email {

    private final String value;

    public Email(String email) {
        requireNonNull(email);
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        this.value = email;
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
        if (obj instanceof Email) {
            Email other = (Email) obj;
            return other.value.equals(value);

        } else {
            return false;
        }
    }

}
