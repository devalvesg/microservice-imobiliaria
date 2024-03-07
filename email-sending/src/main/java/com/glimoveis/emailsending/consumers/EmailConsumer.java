package com.glimoveis.emailsending.consumers;

import com.glimoveis.emailsending.controllers.EmailController;
import com.glimoveis.emailsending.dtos.EmailDTO;
import com.glimoveis.emailsending.dtos.UserResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private EmailController emailController;
    public EmailConsumer(EmailController emailController){
        this.emailController = emailController;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload UserResponse userResponse){
        emailController.boasVindas(userResponse);
    }
}
