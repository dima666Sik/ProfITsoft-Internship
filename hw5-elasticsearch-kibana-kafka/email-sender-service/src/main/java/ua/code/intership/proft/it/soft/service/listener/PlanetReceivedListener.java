package ua.code.intership.proft.it.soft.service.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ua.code.intership.proft.it.soft.domain.dto.message.PlanetReceivedMessage;
import ua.code.intership.proft.it.soft.service.EmailService;

@Component
@RequiredArgsConstructor
public class PlanetReceivedListener {
    private final EmailService emailService;

    @KafkaListener(topics = "${kafka.topic.planetReceived}")
    public void planetReceived(PlanetReceivedMessage message) {
        emailService.processEmailReceived(message);
    }
}
