package com.mycompany.myapp.api.helloworld;

import com.mycompany.myapp.JsonParser;
import com.mycompany.myapp.MyAppApplictionMock;
import net.watertao.springsock.infras.framework.APIResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= MyAppApplictionMock.class)
public class TestHelloWorldService {

    @Autowired
    private HelloWorldService helloWorldService;


    @Test
    public void test() throws Exception {

        HelloWorldRequest request = JsonParser.parse(
                HelloWorldService.class,
                HelloWorldRequest.class,
                "test.json");

        APIResponse response = helloWorldService.call(request);

        System.out.println(response);

    }


}
