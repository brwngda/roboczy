package com.gbarwinski.organizerspring.rabbitMQ.MessagingApplicationIT;


import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.gbarwinski.organizerspring.rabbitMQ.MessageSenderTest.MessageSenderTest;
import lombok.val;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = MessagingApplicationIT.Initializer.class)
public class MessagingApplicationIT {
    @ClassRule
    public static GenericContainer rabbit = new GenericContainer("rabbitmq:3-management")
            .withExposedPorts(5672, 15672);
    @Rule
    public OutputCapture outputCapture = new OutputCapture();
    @Autowired
    private MessageSenderTest.MessageSender messageSender;
    @Test
    public void testBroadcast() {
        messageSender.broadcast("Broadcast Test");
        await().atMost(5, TimeUnit.SECONDS).until(isMessageConsumed(), is(true));
    }
    private Callable<Boolean> isMessageConsumed() {
        return () -> outputCapture.toString().contains("Broadcast Test");
    }
    public static class Initializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            val values = TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbit.getContainerIpAddress(),
                    "spring.rabbitmq.port=" + rabbit.getMappedPort(5672)
            );
            values.applyTo(configurableApplicationContext);
        }
    }
}
