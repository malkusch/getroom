package de.malkusch.getroom.model.apply;

import java.io.IOException;

import org.springframework.retry.annotation.Retryable;

import de.malkusch.getroom.model.Room;

public interface ApplyService {

    @Retryable(IOException.class)
    void apply(Room room, Letter letter) throws IOException;

}
