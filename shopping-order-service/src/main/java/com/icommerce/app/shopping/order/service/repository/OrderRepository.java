package com.icommerce.app.shopping.order.service.repository;

import com.icommerce.app.shopping.order.service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByIdAndUserId(Long id, Long userId);
}
