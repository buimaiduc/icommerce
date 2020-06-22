package com.icommerce.app.shopping.common.authentication.service;

import com.icommerce.app.shopping.common.authentication.authentication.User;

public interface CurrentUserService {

    User getCurrentUserLogin();

    String getCurrentUserLogin(String authorizationToken, String secretKey);
}