package com.lbytech.QAGenerator.controller;

import com.lbytech.QAGenerator.entity.dto.ChatForm;
import com.lbytech.QAGenerator.service.GeneratorAssistant;
import com.lbytech.QAGenerator.service.GeneratorAssistantWithRag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 前端控制器
 */
@RestController
@RequestMapping("/generator")
@Tag(name = "QA测试集生成接口", description = "用于生成QA测试集的接口")
public class GeneratorController {

    @Autowired
    private GeneratorAssistant generatorAssistant;

    @Autowired
    private GeneratorAssistantWithRag generatorAssistantWithRag;

    @Operation(summary = "测试集生成（普通）")
    @PostMapping(value = "/generate",produces = "text/html;charset=UTF-8")
    public Flux<String> gengerate(@RequestBody ChatForm form) {
        return generatorAssistant.chatByStream(form.getId(), form.getMessage());
    }

    @Operation(summary = "测试集生成（rag）")
    @PostMapping(value = "/generateWithRag",produces = "text/html;charset=UTF-8")
    public Flux<String> gengerateWithRg(@RequestBody ChatForm form) {
        return generatorAssistantWithRag.chatByStream(form.getId(), form.getMessage());
    }

}
