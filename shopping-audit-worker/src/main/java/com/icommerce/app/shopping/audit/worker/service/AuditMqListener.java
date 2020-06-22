package com.icommerce.app.shopping.audit.worker.service;

import com.icommerce.app.shopping.audit.worker.model.AuditLog;
import com.icommerce.app.shopping.audit.worker.repository.AuditLogRepository;
import com.icommerce.app.shopping.common.event.message.AuditMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import static com.icommerce.app.shopping.audit.worker.config.RabbitMqConfig.INPUT_ROUTING_KEY;
import static com.icommerce.app.shopping.audit.worker.config.RabbitMqConfig.TEXT_RETRY_AUDIT;

@RefreshScope
@Component
@RabbitListener(id = "AuditListener", queues = "#{auditLogsQueue.name}", autoStartup = "${app.mq.autoStartup}",
        ackMode = "MANUAL", containerFactory = "jsaFactory", concurrency = "${app.mq.concurrency}")
public class AuditMqListener extends BaseRabbitMqListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditMqListener.class);

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public void executeLogic(AuditMessage msg) {
        AuditLog auditLog = new AuditLog();
        this.buildAuditLog(auditLog, msg);
        auditLogRepository.insert(auditLog);

        LOGGER.info("Audit log recorded in Mongo db");
    }

    @Override
    public void toRetryQueue(AuditMessage msg) {
        rabbitMqTemplate.convertAndSend(retryExchangeName, String.format(INPUT_ROUTING_KEY, TEXT_RETRY_AUDIT), msg);
    }

    private void buildAuditLog(AuditLog auditLog, AuditMessage auditMessage) {
        auditLog.setId(auditMessage.getId());
        auditLog.setCreatedAt(auditMessage.getCreatedAt());
        auditLog.setAuditLogType(auditMessage.getAuditLogType().getLogType());
        auditLog.setUserId(auditMessage.getUserId());
        auditLog.setDetail(auditMessage.getDetail());
    }
}
