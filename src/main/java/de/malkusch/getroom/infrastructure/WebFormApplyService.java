package de.malkusch.getroom.infrastructure;

import java.io.IOException;

import org.springframework.stereotype.Service;

import de.malkusch.getroom.model.Room;
import de.malkusch.getroom.model.apply.ApplyService;
import de.malkusch.getroom.model.apply.Letter;

@Service
class WebFormApplyService implements ApplyService {

    @Override
    public void apply(Room room, Letter letter) throws IOException {
        // TODO Auto-generated method stub

    }

}
