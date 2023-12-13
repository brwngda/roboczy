package com.gbarwinski.organizerspring.main_content.services;

import com.gbarwinski.organizerspring.main_content.model.User;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.gbarwinski.organizerspring.main_content.config.RabbitMqConfig.getDirectExchange;

@Service
@RequiredArgsConstructor

public class RabbitService {

    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitService.class);
    private final DirectExchange newDirectExchange;
    private final UserService userService;

    @PostConstruct
    public void createQueuesForAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(user -> createQueue(user.getIdUser().toString()));
    }

    public void createQueue(String userId) {
        String exchange = "organizer";
        DirectExchange organizer;
        boolean exists = isExchangeExists(exchange);
        if (exists) {
            organizer = getDirectExchange();
        } else {
            organizer = newDirectExchange;
        }
        String queueName = "taskInformation." + userId;

        Queue queue = new Queue(queueName);
        amqpAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(organizer).with(queueName);
        amqpAdmin.declareBinding(binding);
    }

    public boolean isExchangeExists(String exchange) {

        return rabbitTemplate.execute(new ChannelCallback<AMQP.Exchange.DeclareOk>() {
            @Override
            public AMQP.Exchange.DeclareOk doInRabbit(Channel channel) throws Exception {
                try {
                    return channel.exchangeDeclarePassive(exchange);
                } catch (Exception e) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Exchange '" + exchange + "' does not exist");
                    }
                    return null;
                } finally {
                    channel.close();
                }
            }
        }) != null;

    }
}
