package de.malkusch.getroom.model;

import static java.util.Objects.requireNonNull;

import java.time.Instant;

public final class Room {

    private final RoomId id;
    private final CreationDate createdAt;

    public Room(RoomId id) {
        this(id, new CreationDate(Instant.now()));
    }

    public Room(RoomId id, CreationDate createdAt) {
        this.id = requireNonNull(id);
        this.createdAt = requireNonNull(createdAt);
    }

    public RoomId id() {
        return id;
    }

    public CreationDate createdAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
