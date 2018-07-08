package de.malkusch.getroom.model;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.retry.annotation.Retryable;

public interface RoomRepository {

    @Retryable(IOException.class)
    Stream<Room> findNewRooms(City city, District[] districts, Price maxPrice) throws IOException;

}
