package com.lbytech.QAGenerator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:environmentVariables.env")
@MapperScan("com.lbytech.QAGenerator.mapper")
public class NetFieldQaFormTestSetGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetFieldQaFormTestSetGeneratorApplication.class, args);
    }

}
