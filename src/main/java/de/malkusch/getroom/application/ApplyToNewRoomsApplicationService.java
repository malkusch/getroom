package de.malkusch.getroom.application;

import java.io.IOException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import de.malkusch.getroom.model.City;
import de.malkusch.getroom.model.CreationDate;
import de.malkusch.getroom.model.District;
import de.malkusch.getroom.model.Price;
import de.malkusch.getroom.model.Room;
import de.malkusch.getroom.model.RoomRepository;
import de.malkusch.getroom.model.apply.ApplyService;
import de.malkusch.getroom.model.apply.Letter;

public final class ApplyToNewRoomsApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplyToNewRoomsApplicationService.class);

    ApplyToNewRoomsApplicationService(RoomRepository rooms, ApplyService applyService, Price maxPrice, City city,
            District[] disctricts, Letter letter) {

        lastRoomCreatedAt = new CreationDate(Instant.now().plusSeconds(60));
        this.rooms = rooms;
        this.maxPrice = maxPrice;
        this.city = city;
        this.districts = disctricts;
        this.applyService = applyService;
        this.letter = letter;

        LOGGER.info("max. price: {}, city: {}", maxPrice, city);
        LOGGER.info("letter: {}, ", letter);
    }

    private final RoomRepository rooms;
    private final ApplyService applyService;
    private final Price maxPrice;
    private final City city;
    private final District[] districts;
    private volatile CreationDate lastRoomCreatedAt;

    @Scheduled(fixedDelayString = "${scrapeDelay}")
    public void applyToNewRooms() throws IOException {
        LOGGER.debug("Checking for new rooms");
        rooms.findNewRooms(city, districts, maxPrice, lastRoomCreatedAt).forEach(this::apply);
    }

    private final Letter letter;

    private void apply(Room room) {
        try {
            LOGGER.info("Applying for room {}", room);
            applyService.apply(room, letter);
            lastRoomCreatedAt = room.createdAt();

        } catch (IOException e) {
            LOGGER.warn("Failed to apply for {}", room, e);
        }
    }

}
