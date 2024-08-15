package com.ms.email.producers;

import java.util.ArrayList;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.EmailModel;

@Component
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(ArrayList<EmailModel> emailModel) {
        var emailDto = new EmailRecordDto(emailModel);
        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
