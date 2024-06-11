package ua.code.intership.proft.it.soft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.code.intership.proft.it.soft.domain.model.Message;
import ua.code.intership.proft.it.soft.model.dto.message.PlanetReceivedMessage;
import ua.code.intership.proft.it.soft.repository.EmailRepository;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;

    @Override
    public void processEmailReceived(PlanetReceivedMessage msg) {
        Message message = Message.builder()
                                 .id(msg.getId())
                                 .subject(msg.getSubject())
                                 .content(msg.getContent())
                                 .emailConsumer(msg.getEmailConsumer())
                                 .build();
        emailRepository.save(message);
    }
}
