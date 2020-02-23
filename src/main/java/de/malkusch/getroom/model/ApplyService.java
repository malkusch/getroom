package de.malkusch.getroom.model;

import java.io.IOException;

import org.springframework.retry.annotation.Retryable;

public interface ApplyService {

    @Retryable(IOException.class)
    void apply(Room room, Letter letter) throws IOException;

}
