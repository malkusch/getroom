package de.malkusch.getroom.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.malkusch.getroom.model.City;
import de.malkusch.getroom.model.Price;
import de.malkusch.getroom.model.RoomRepository;
import de.malkusch.getroom.model.apply.ApplyService;
import de.malkusch.getroom.model.apply.Letter;

@Configuration
class ApplicationConfiguration {

    @Bean
    ApplyToNewRoomsApplicationService applyToNewRoomsApplicationService(RoomRepository rooms, ApplyService applyService,
            @Value("${maxPrice}") int maxPrice, @Value("${city}") int city) {

        Letter letter = new Letter();
        return new ApplyToNewRoomsApplicationService(rooms, applyService, new Price(maxPrice), new City(city), letter);
    }

}
