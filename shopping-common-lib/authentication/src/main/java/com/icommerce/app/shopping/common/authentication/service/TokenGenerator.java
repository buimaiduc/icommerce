package com.icommerce.app.shopping.common.authentication.service;

import com.icommerce.app.shopping.common.authentication.authentication.User;

import java.util.concurrent.TimeUnit;

public interface TokenGenerator {

    String generate(User user, int expiredTime, TimeUnit timeUnit);
}
