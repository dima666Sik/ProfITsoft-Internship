package ua.code.intership.proft.it.soft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.code.intership.proft.it.soft.domain.model.Message;
import ua.code.intership.proft.it.soft.model.dto.message.PlanetReceivedMessage;
import ua.code.intership.proft.it.soft.repository.EmailRepository;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.email}")
    private String emailAdmin;
    @Value("${fake.email.sender}")
    private String fakeEmailSender;

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
        sendMessageOnEmail(message);
    }

    private void sendMessageOnEmail(Message msg){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fakeEmailSender);
        message.setTo(emailAdmin);
        message.setSubject(msg.getSubject());
        message.setText(msg.getContent());
        mailSender.send(message);
    }
}
