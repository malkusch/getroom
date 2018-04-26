package de.malkusch.getroom.model;

import static java.util.Objects.requireNonNull;

public final class RoomId {

    private final String value;

    public RoomId(String id) {
        requireNonNull(id);
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Room id must not be empty");
        }
        this.value = id;
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
        if (obj instanceof RoomId) {
            RoomId other = (RoomId) obj;
            return other.value.equals(value);

        } else {
            return false;
        }
    }

}
