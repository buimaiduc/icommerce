package com.icommerce.app.shopping.user.service.rest.request;

public class UserLoginRequest {
    private String facebookToken;

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }
}
