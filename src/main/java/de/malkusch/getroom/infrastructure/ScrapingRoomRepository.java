package de.malkusch.getroom.infrastructure;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import de.malkusch.getroom.model.City;
import de.malkusch.getroom.model.CreationDate;
import de.malkusch.getroom.model.Price;
import de.malkusch.getroom.model.Room;
import de.malkusch.getroom.model.RoomRepository;

@Service
final class ScrapingRoomRepository implements RoomRepository {

    @Override
    public Stream<Room> findNewRooms(City city, Price maxPrice, CreationDate since) throws IOException {
        return null;
    }

}
