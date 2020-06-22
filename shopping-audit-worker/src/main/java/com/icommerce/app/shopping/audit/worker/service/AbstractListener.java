package com.icommerce.app.shopping.audit.worker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractListener {
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @Value("${app.mq.audit.topic.exchangeName}")
    protected String topicExchangeName;

    @Value("${app.mq.audit.retry.exchangeName}")
    protected String retryExchangeName;

    @Autowired
    protected RabbitTemplate rabbitMqTemplate;

}

