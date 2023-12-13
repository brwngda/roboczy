package com.gbarwinski.organizerspring.main_content.controllers.RestControllersRabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientMq {


    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/receivetaskInformation/{userId}")

    public String get(@PathVariable String userId) {

        Object message = rabbitTemplate.receiveAndConvert("taskInformation." + userId);
        if (message == null)
            return "Nie ma nowych wiadomości";
        else {
            return message.toString();
        }
    }
}

