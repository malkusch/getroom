package de.malkusch.getroom.model;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.retry.annotation.Retryable;

public interface RoomRepository {

    @Retryable(IOException.class)
    Stream<Room> findNewRooms(City city, Price maxPrice, CreationDate since) throws IOException;

}
