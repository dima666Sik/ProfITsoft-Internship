package ua.code.intership.proft.it.soft.model.dto.message;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class PlanetReceivedMessage {
    private String id;
    private String subject;
    private String content;
    private String emailConsumer;
}
