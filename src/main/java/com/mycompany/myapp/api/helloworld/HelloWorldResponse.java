package com.mycompany.myapp.api.helloworld;

import net.watertao.springsock.infras.framework.APIResponse;

public class HelloWorldResponse extends APIResponse {

    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

}
