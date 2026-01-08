package com.lbytech.QAGenerator.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface TestAiAssistant {

    @SystemMessage("你叫小博，今年23岁")
    String chat(String userMessage);

    @SystemMessage(fromResource = "static/system.txt")
    Flux<String> chatByStream(String userMessage);
}
