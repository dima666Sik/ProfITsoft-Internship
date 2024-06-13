package ua.code.intership.proft.it.soft.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.code.intership.proft.it.soft.EmailSenderApplication;
import ua.code.intership.proft.it.soft.domain.dto.message.PlanetReceivedMessage;
import ua.code.intership.proft.it.soft.domain.model.Message;
import ua.code.intership.proft.it.soft.repository.EmailRepository;
import ua.code.intership.proft.it.soft.service.config.TestElasticSearchConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmailSenderApplication.class, TestElasticSearchConfiguration.class})
class EmailServiceImplTest {
    @Autowired
    private EmailServiceImpl emailService;

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private EmailRepository emailRepository;
    private Message msg;
    private PlanetReceivedMessage planetReceivedMessage;

    @BeforeEach
    void init() {
        planetReceivedMessage = PlanetReceivedMessage.builder()
                                                     .id("1")
                                                     .subject("Subject")
                                                     .content("Content")
                                                     .emailConsumer("EmailConsumer")
                                                     .build();

        msg = Message.builder()
                     .id(planetReceivedMessage.getId())
                     .emailConsumer(planetReceivedMessage.getEmailConsumer())
                     .content(planetReceivedMessage.getContent())
                     .subject(planetReceivedMessage.getSubject())
                     .retryCount(0)
                     .status("Success")
                .build();
    }

    @Test
    void processEmailReceivedShouldSuccessful() {

        doNothing().when(mailSender)
                   .send(any(SimpleMailMessage.class));

        when(emailRepository.save(any(Message.class))).thenReturn(msg);

        emailService.processEmailReceived(planetReceivedMessage);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        verify(emailRepository, times(1)).save(any(Message.class));

    }

    @Test
    void processEmailReceivedShouldFailed() {
        Message failedMessage = msg;
        failedMessage.setId("1");
        failedMessage.setStatus("Error");
        failedMessage.setSubject("Test Subject");
        failedMessage.setContent("Test Content");

        when(emailRepository.findByStatus("Error")).thenReturn(Collections.singletonList(failedMessage));

        doThrow(new MailException("Test Exception") {
        }).when(mailSender)
          .send(any(SimpleMailMessage.class));

        emailService.retryFailedEmails();

        verify(emailRepository, times(1)).save(failedMessage);
        Assertions.assertEquals(1, failedMessage.getRetryCount());
    }

}