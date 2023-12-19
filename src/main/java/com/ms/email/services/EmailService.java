package com.ms.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.EmailModel;
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

        var emailModel = new EmailModel();

        BeanUtils.copyProperties(emailRecordDto, emailModel);

        emailProducer.publishMessageEmail(emailModel);

        return ResponseEntity.status(200).body("E-mail Enviado com sucesso!");
    }

    @Transactional
    public void sendMail(EmailModel emailModel) {
        try {
            emailModel.setCreated_at(LocalDateTime.now());
            emailModel.setEmail(emailModel.getEmail());

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(emailModel.getEmail());
            message.setSubject("E-mail recebido com sucesso!");
            message.setText("Microservices enviar email java + spring boot");

            emailSender.send(message);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        } finally {
            this.saveEmail(emailModel);
        }
    }

    public void saveEmail(EmailModel emailModel) {
        try {
            emailRepository.save(emailModel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
