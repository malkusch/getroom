package de.malkusch.getroom.model.apply;

import static java.util.Objects.requireNonNull;

public final class Letter {

    public Letter(String message) {
        requireNonNull(message);
        if (message.isEmpty()) {
            throw new IllegalArgumentException("Message must not be empty");
        }
        this.message = message;
    }

    private final String message;

    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return message();
    }

}
