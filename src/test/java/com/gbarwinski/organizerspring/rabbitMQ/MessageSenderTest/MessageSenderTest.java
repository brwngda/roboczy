package com.gbarwinski.organizerspring.rabbitMQ.MessageSenderTest;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;

import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

public class MessageSenderTest {
    private MessageSender subject;
    private  RabbitTemplate rabbitTemplateMock;
    private  DirectExchange newDirectExchange;
    @Before
    public void setUp() {
        this.rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
        this.subject = new MessageSender(rabbitTemplateMock,newDirectExchange);
    }

    @Test
    public void testBroadcast() {
        assertThatCode(() -> this.subject.broadcast("Test")).doesNotThrowAnyException();
        Mockito.verify(this.rabbitTemplateMock)
                .convertAndSend(eq("organizer"), eq("taskInformation.1"), eq("Test"));
    }

    @Component
    public static class MessageReceiver {
        @RabbitListener(queues = {"taskInformation.1"})
        public void receiveMessageFromFanout(String message) {
            System.out.println("Received broadcast message: " + message);
        }

    }

    @Component
    @RequiredArgsConstructor
    public static class MessageSender {
        private final RabbitTemplate rabbitTemplate;
        private final DirectExchange newDirectExchange;



        public void broadcast(String message) {

            DirectExchange organizer=newDirectExchange;
            rabbitTemplate.convertAndSend("organizer", "taskInformation.1", message);
        }
        public void sendError(String message) {
            DirectExchange organizer=newDirectExchange;

            this.rabbitTemplate.convertAndSend(organizer.getName(), "this.is.an.error", message);
        }
    }
}