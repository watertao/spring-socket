package com.mycompany.myapp;

import net.watertao.springsock.infras.netty.SMSPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.Locale;

@SpringBootApplication
@Configuration
@ComponentScan(
        value={"com.mycompany.myapp", "net.watertao.springsock"},
        excludeFilters = @ComponentScan.Filter(
                type= FilterType.ASSIGNABLE_TYPE, value= {SMSPServer.class, MyAppApplication.class}
        )
)
public class MyAppApplictionMock {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(MyAppApplictionMock.class, args);
    }

}
