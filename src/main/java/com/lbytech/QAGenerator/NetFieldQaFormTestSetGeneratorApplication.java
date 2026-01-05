package com.lbytech.QAGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:environmentVariables.env")
public class NetFieldQaFormTestSetGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetFieldQaFormTestSetGeneratorApplication.class, args);
    }

}
