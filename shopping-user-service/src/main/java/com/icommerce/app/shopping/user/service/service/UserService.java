package com.icommerce.app.shopping.user.service.service;

import com.icommerce.app.shopping.user.service.model.User;
import com.icommerce.app.shopping.user.service.rest.response.UserLoginResponse;

public interface UserService {
    UserLoginResponse loginWithFacebook(String facebookToken);

    User findById(Long id);
}
