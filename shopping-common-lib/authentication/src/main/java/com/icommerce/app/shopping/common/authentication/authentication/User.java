package com.icommerce.app.shopping.common.authentication.authentication;

import java.security.Principal;

public class User implements Principal {
    private Long id;
    private String email;
    private String name;

    public User() {
        // default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":\"%s\",\"email\":\"%s\",\"name\":\"%s\"}", id, email, name);
    }
}