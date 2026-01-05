package com.lbytech.QAGenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "测试接口", description = "用于测试的接口")
public class TestController {

    @Operation(summary = "测试string")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
