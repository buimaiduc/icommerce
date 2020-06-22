package com.icommerce.app.shopping.audit.worker.service;

import com.icommerce.app.shopping.audit.worker.exception.RedeliverableException;
import com.icommerce.app.shopping.common.event.message.AuditMessage;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RefreshScope
@Component
public abstract class BaseRabbitMqListener extends AbstractListener {
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @RabbitHandler
    public void receive(AuditMessage msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        LOGGER.info("Received msg : " + msg.toString());
        try {
            executeLogic(msg);
        } catch (RedeliverableException ex) {
            AuditMessage clonedMessage = msg.clone();
            toRetryQueue(clonedMessage);
        } catch (Exception ex) {
            LOGGER.error("Failed to handle message");
        } finally {
            channel.basicAck(tag, false);
        }
    }

    protected abstract void executeLogic(AuditMessage msg) throws Exception;

    protected abstract void toRetryQueue(AuditMessage msg);
}