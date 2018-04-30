package de.malkusch.getroom.infrastructure;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.malkusch.getroom.model.Room;
import de.malkusch.getroom.model.apply.ApplyService;
import de.malkusch.getroom.model.apply.Letter;

@Service
@Profile("dev")
@Primary
class NullApplyService implements ApplyService {

    private static final Logger LOOGER = LoggerFactory.getLogger(NullApplyService.class);

    @Override
    public void apply(Room room, Letter letter) throws IOException {
        LOOGER.info("Ignore application for {}", room);
    }

}
