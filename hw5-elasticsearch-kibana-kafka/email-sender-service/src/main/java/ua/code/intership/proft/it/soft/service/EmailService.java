package ua.code.intership.proft.it.soft.service;


import ua.code.intership.proft.it.soft.model.dto.message.PlanetReceivedMessage;

public interface EmailService {
    void processEmailReceived(PlanetReceivedMessage message);
}
