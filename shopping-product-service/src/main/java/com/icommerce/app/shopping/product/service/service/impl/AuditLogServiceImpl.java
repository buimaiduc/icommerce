package com.icommerce.app.shopping.product.service.service.impl;

import com.icommerce.app.shopping.common.event.message.AuditLogType;
import com.icommerce.app.shopping.common.event.message.AuditMessage;
import com.icommerce.app.shopping.product.service.service.AuditLogService;
import com.icommerce.app.shopping.product.service.service.MessageQueueService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogServiceImpl.class);
    private static final String MESSAGE_CONTENT = "User %s %s product %s";
    private static final String ANONYMOUS = "Anonymous";

    @Autowired
    private MessageQueueService messageQueueService;

    @Override
    public void log(Long userId, Long productId, AuditLogType auditLogType) {
        AuditMessage auditMessage = new AuditMessage();
        auditMessage.setId(UUID.randomUUID().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        auditMessage.setCreatedAt(simpleDateFormat.format(new Date()));
        auditMessage.setAuditLogType(auditLogType);
        auditMessage.setUserId(userId);
        if (StringUtils.isEmpty(auditMessage.getDetail())) {
            if (userId == -1) {
                auditMessage.setDetail(String.format(MESSAGE_CONTENT, ANONYMOUS, auditLogType.getLogType(), productId));
            } else {
                auditMessage.setDetail(String.format(MESSAGE_CONTENT, userId, auditLogType.getLogType(), productId));
            }
        }

        LOGGER.debug("Publish audit message: detail: {}, logType: {}", auditMessage.getDetail(), auditMessage.getAuditLogType());
        messageQueueService.sendMessage(auditMessage);
    }

    @Override
    public void log(Long userId, String detail, AuditLogType auditLogType) {
        AuditMessage auditMessage = new AuditMessage();
        auditMessage.setId(UUID.randomUUID().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        auditMessage.setCreatedAt(simpleDateFormat.format(new Date()));
        auditMessage.setAuditLogType(auditLogType);
        auditMessage.setUserId(userId);
        auditMessage.setDetail(detail);

        LOGGER.debug("Publish audit message: detail: {}, logType: {}", auditMessage.getDetail(), auditMessage.getAuditLogType());
        messageQueueService.sendMessage(auditMessage);
    }
}
