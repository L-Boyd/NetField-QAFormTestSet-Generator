package com.lbytech.QAGenerator.controller;

import com.lbytech.QAGenerator.entity.dto.ChatForm;
import dev.langchain4j.model.chat.ChatModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Tag(name = "测试接口", description = "用于测试的接口")
public class TestController {

    @Autowired
    private ChatModel chatModel;

    @Operation(summary = "测试hello world")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @Operation(summary = "测试chat")
    @PostMapping("/chat")
    public String testChat(@RequestBody ChatForm form) {
        return chatModel.chat(form.getMessage());
    }
}
