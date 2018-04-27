package de.malkusch.getroom.model.apply;

import static java.util.Objects.requireNonNull;

public final class Letter {

    public Letter(Candidate candidate, String message) {
        this.candidate = requireNonNull(candidate);

        requireNonNull(message);
        if (message.isEmpty()) {
            throw new IllegalArgumentException("Message must not be empty");
        }
        this.message = message;
    }

    private final Candidate candidate;

    public Candidate candidate() {
        return candidate;
    }

    private final String message;

    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return candidate() + ":\n" + message();
    }

}
