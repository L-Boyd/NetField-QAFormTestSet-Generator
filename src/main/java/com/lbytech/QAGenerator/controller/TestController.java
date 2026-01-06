package com.lbytech.QAGenerator.controller;

import com.lbytech.QAGenerator.entity.dto.ChatForm;
import com.lbytech.QAGenerator.service.TestAiAssistant;
import dev.langchain4j.model.chat.ChatModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/test")
@Tag(name = "测试接口", description = "用于测试的接口")
public class TestController {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private TestAiAssistant testAiAssistant;

    @Operation(summary = "测试hello world")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @Operation(summary = "测试chatModel的chat")
    @PostMapping("/chat")
    public String testChat(@RequestBody ChatForm form) {
        return chatModel.chat(form.getMessage());
    }

    @Operation(summary = "测试AiService的chat")
    @PostMapping("/chat-assistant")
    public String testChatAssistant(@RequestBody ChatForm form) {
        return testAiAssistant.chat(form.getMessage());
    }

    @Operation(summary = "测试AiService的chatByStream流式响应")
    @PostMapping(value = "/chat-assistant-stream",produces = "text/html;charset=UTF-8")
    public Flux<String> testChatAssistantStream(@RequestBody ChatForm form) {
        return testAiAssistant.chatByStream(form.getMessage());
    }

}
