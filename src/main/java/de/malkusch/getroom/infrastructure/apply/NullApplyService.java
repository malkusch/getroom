package de.malkusch.getroom.infrastructure.apply;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.malkusch.getroom.model.ApplyService;
import de.malkusch.getroom.model.Letter;
import de.malkusch.getroom.model.Room;

@Service
@Profile("dev")
@Primary
class NullApplyService implements ApplyService {

    private static final Logger LOGGER = getLogger(NullApplyService.class);

    @Override
    public void apply(Room room, Letter letter) throws IOException {
        LOGGER.info("Ignore application for {}", room);
    }
}
