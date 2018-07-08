package de.malkusch.getroom.model;

import static java.util.Objects.requireNonNull;

public final class Room {

    private final RoomId id;

    public Room(RoomId id, boolean contacted) {
        this.id = requireNonNull(id);
        this.applied = contacted;
    }

    public RoomId id() {
        return id;
    }

    private final boolean applied;

    public boolean isApplied() {
        return applied;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
