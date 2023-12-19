package com.ms.email.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.producers.EmailProducer;
import com.ms.email.repositories.EmailRepository;

public class EmailServiceTest {

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailProducer emailProducer;

    @Mock
    private JavaMailSender emailSender;

    @Autowired
    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPublishMessage() {

        var emailRecordDto = new EmailRecordDto("testando@teste.com");

        ResponseEntity<Object> teste = emailService.publishMessage(emailRecordDto);

        assertEquals("E-mail Enviado com sucesso!", teste.getBody());
    }

}
