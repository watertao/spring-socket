package com.mycompany.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Locale;


@SpringBootApplication
@ComponentScan({"com.mycompany.myapp", "net.watertao.springsock"})
public class MyAppApplication {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(MyAppApplication.class, args);
    }

}