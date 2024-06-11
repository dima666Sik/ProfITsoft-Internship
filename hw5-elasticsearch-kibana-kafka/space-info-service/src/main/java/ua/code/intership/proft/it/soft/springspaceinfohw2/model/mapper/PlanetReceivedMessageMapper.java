package ua.code.intership.proft.it.soft.springspaceinfohw2.model.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.code.intership.proft.it.soft.model.dto.message.PlanetReceivedMessage;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.dto.request.PlanetRequestDto;

@Component
public final class PlanetReceivedMessageMapper {
    @Value("${admin.email}")
    private String adminEmail;

    public PlanetReceivedMessage toMessage(PlanetRequestDto dto) {
        return PlanetReceivedMessage.builder()
                                    .id(String.valueOf(dto.id()))
                                    .subject("Planet: "+dto.name())
                                    .content(dto.toString())
                                    .emailConsumer(adminEmail)
                                    .build();
    }
}
