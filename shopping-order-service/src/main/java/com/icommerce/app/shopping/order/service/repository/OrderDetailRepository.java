package com.icommerce.app.shopping.order.service.repository;

import com.icommerce.app.shopping.order.service.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
