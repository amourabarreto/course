package com.ead.course.publishers;

import com.ead.course.dtos.NotificationCommandDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationCommandPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${ead.broker.exchange.notificationCommandExchange}")
    private String exchange;

    @Value(value = "${ead.broker.key.notificationCommandKey}")
    private String key;

    public void publish(NotificationCommandDto notificationCommandDto){
        rabbitTemplate.convertAndSend(exchange,key,notificationCommandDto);
    }
}
