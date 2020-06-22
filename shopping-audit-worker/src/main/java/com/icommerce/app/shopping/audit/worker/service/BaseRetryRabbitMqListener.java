package com.icommerce.app.shopping.audit.worker.service;

import com.icommerce.app.shopping.audit.worker.exception.RedeliverableException;
import com.icommerce.app.shopping.common.event.message.AuditMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RefreshScope
@Component
public abstract class BaseRetryRabbitMqListener extends AbstractListener {

    @Value("${app.mq.maxRetry:0}")
    private int maxRetry;

    @RabbitHandler
    public void receive(AuditMessage msg, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                        @Header(required = false, name = "x-death") List<Map<String, Object>> xDeath) throws Exception {

        int currentRedelivered = msg.getRedeliveredCount();

        if (currentRedelivered >= maxRetry) {
            LOGGER.info("Retries exceeded putting into parking lot or drop");
            // skip this msg
            channel.basicAck(tag, false);
            return;
        }

        try {
            executeLogic(msg);
        } catch (RedeliverableException ex) {
            LOGGER.info("To requeue");
            toRetryQueue(msg);
        } catch (Exception ex) {
            LOGGER.error("Failed to handle retry message");
        } finally {
            channel.basicAck(tag, false);
        }
    }

    protected abstract void executeLogic(AuditMessage msg) throws Exception;

    protected abstract void toRetryQueue(AuditMessage msg);

}