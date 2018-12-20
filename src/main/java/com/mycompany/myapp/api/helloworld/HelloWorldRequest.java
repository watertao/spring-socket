package com.mycompany.myapp.api.helloworld;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class HelloWorldRequest {

    @NotNull
    @NotEmpty
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
