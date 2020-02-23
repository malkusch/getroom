package de.malkusch.getroom.application;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.malkusch.getroom.model.ApplyService;
import de.malkusch.getroom.model.City;
import de.malkusch.getroom.model.District;
import de.malkusch.getroom.model.Letter;
import de.malkusch.getroom.model.Price;
import de.malkusch.getroom.model.RoomRepository;

@Configuration
class ApplicationConfiguration {

    @Bean
    Letter letter(@Value("${letter.message}") String message) {
        Letter letter = new Letter(message);
        return letter;
    }

    @Bean
    ApplyToNewRoomsApplicationService applyToNewRoomsApplicationService(RoomRepository rooms, ApplyService applyService,
            @Value("${maxPrice}") int maxPrice, @Value("${city}") int city, @Value("${districts}") int[] districts,
            Letter letter) {

        return new ApplyToNewRoomsApplicationService(rooms, applyService, new Price(maxPrice), new City(city),
                districts(districts), letter);
    }

    private static District[] districts(int[] districts) {
        return Arrays.stream(districts).mapToObj(District::new).toArray(District[]::new);
    }

}
