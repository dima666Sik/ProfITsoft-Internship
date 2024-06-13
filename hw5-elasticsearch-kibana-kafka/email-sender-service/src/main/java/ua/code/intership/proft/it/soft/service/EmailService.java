package ua.code.intership.proft.it.soft.service;


import ua.code.intership.proft.it.soft.domain.dto.message.PlanetReceivedMessage;

public interface EmailService {
    void processEmailReceived(PlanetReceivedMessage message);
}
