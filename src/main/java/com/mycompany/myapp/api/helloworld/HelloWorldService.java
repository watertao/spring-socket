package com.mycompany.myapp.api.helloworld;

import net.watertao.springsock.infras.framework.API;
import net.watertao.springsock.infras.framework.APIException;
import net.watertao.springsock.infras.framework.APIResponse;
import net.watertao.springsock.infras.framework.AbstractAPIService;
import org.springframework.stereotype.Component;


@API("0001")
@Component
public class HelloWorldService extends AbstractAPIService<HelloWorldRequest> {

    @Override
    public APIResponse call(HelloWorldRequest request) throws APIException {

        HelloWorldResponse response = new HelloWorldResponse();
        response.setGreeting("hello " + request.getUser());

        return response;
    }

}
