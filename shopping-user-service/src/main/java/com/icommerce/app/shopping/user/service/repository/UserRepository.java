package com.icommerce.app.shopping.user.service.repository;

import com.icommerce.app.shopping.user.service.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
