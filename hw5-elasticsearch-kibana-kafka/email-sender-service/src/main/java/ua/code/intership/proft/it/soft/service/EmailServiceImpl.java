package ua.code.intership.proft.it.soft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.code.intership.proft.it.soft.domain.model.Message;
import ua.code.intership.proft.it.soft.model.dto.message.PlanetReceivedMessage;
import ua.code.intership.proft.it.soft.repository.EmailRepository;

import java.time.LocalDateTime;
import java.util.List;

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
                                 .status("Success")
                                 .retryCount(0)
                                 .build();
        sendMessageAndUpdateStatus(message);
    }

    @Scheduled(fixedRate = 300000) // 5 minutes
    public void retryFailedEmails() {
        List<Message> failedMessages = emailRepository.findByStatus("Error");
        failedMessages.forEach(this::sendMessageAndUpdateStatus);
    }

    private void sendMessageAndUpdateStatus(Message msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fakeEmailSender);
        message.setTo(emailAdmin);
        message.setSubject(msg.getSubject());
        message.setText(msg.getContent());
        try {
            msg.setStatus("Success");
            msg.setErrorMsg(null);
            mailSender.send(message);
        } catch (MailException ex) {
            msg.setStatus("Error");
            msg.setErrorMsg(ex.getClass().getName() + ": " + ex.getMessage());
            msg.setRetryCount(msg.getRetryCount() + 1);
            msg.setLastAttemptTime(LocalDateTime.now().toString());
        }
        emailRepository.save(msg);
    }
}
