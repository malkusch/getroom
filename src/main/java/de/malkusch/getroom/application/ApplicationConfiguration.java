package de.malkusch.getroom.application;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.malkusch.getroom.model.City;
import de.malkusch.getroom.model.District;
import de.malkusch.getroom.model.Price;
import de.malkusch.getroom.model.RoomRepository;
import de.malkusch.getroom.model.apply.ApplyService;
import de.malkusch.getroom.model.apply.Candidate;
import de.malkusch.getroom.model.apply.Email;
import de.malkusch.getroom.model.apply.FirstName;
import de.malkusch.getroom.model.apply.Gender;
import de.malkusch.getroom.model.apply.LastName;
import de.malkusch.getroom.model.apply.Letter;
import de.malkusch.getroom.model.apply.Phone;

@Configuration
class ApplicationConfiguration {

    @Bean
    Letter letter(@Value("${letter.gender}") Gender gender, @Value("${letter.firstName}") String firstName,
            @Value("${letter.lastName}") String lastName, @Value("${letter.email}") String email,
            @Value("${letter.phone}") String phone, @Value("${letter.message}") String message) {

        Candidate candidate = new Candidate(gender, new FirstName(firstName), new LastName(lastName), new Email(email),
                new Phone(phone));
        Letter letter = new Letter(candidate, message);
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
