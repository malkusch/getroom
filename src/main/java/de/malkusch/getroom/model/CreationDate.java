package de.malkusch.getroom.model;

import static java.util.Objects.requireNonNull;

import java.time.Instant;

public final class CreationDate {

    private final Instant date;

    public CreationDate(Instant date) {
        this.date = requireNonNull(date);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    public boolean isAfter(CreationDate other) {
        requireNonNull(other);
        return date.isAfter(other.date);
    }

}
