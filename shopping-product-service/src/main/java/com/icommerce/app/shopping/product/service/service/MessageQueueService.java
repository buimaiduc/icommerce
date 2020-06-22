package com.icommerce.app.shopping.product.service.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class MessageQueueService {

    public static final String INPUT_ROUTING_KEY = "%s.mq.worker";
    public static final String TEXT_AUDIT = "audit";

    @Value("${app.mq.audit.topic.exchangeName}")
    private String topicExchangeName;

    @Autowired
    private RabbitTemplate rabbitMqTemplate;

    public <T> void sendMessage(T msg) {
        rabbitMqTemplate.convertAndSend(topicExchangeName, String.format(INPUT_ROUTING_KEY, TEXT_AUDIT), msg);
    }
}