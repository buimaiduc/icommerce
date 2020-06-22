package com.icommerce.app.shopping.audit.worker.repository;

import com.icommerce.app.shopping.audit.worker.model.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

}