package com.ms.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.producers.EmailProducer;
import com.ms.email.repositories.EmailRepository;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailProducer emailProducer;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public ResponseEntity<Object> publishMessage(EmailRecordDto emailRecordDto) {

        emailProducer.publishMessageEmail(emailRecordDto.emails());

        return ResponseEntity.status(200).body("E-mail Enviado com sucesso!");
    }

    @Transactional
    public void sendMail(EmailRecordDto emailRecordDto) {
        try {

            for (int i = 0; i < emailRecordDto.emails().size(); i++) {
                emailRecordDto.emails().get(i).setCreated_at(LocalDateTime.now());

                SimpleMailMessage message = new SimpleMailMessage();

                message.setTo(emailRecordDto.emails().get(i).getEmail());
                message.setSubject("E-mail recebido com sucesso!");
                message.setText("Microservices enviar email java + spring boot");

                emailSender.send(message);
            }

        } catch (MailException e) {
            System.out.println(e.getMessage());
        } finally {
            this.saveEmail(emailRecordDto);
        }
    }

    @Transactional
    public void saveEmail(EmailRecordDto emailRecordDto) {
        try {
            emailRepository.saveAll(emailRecordDto.emails());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
