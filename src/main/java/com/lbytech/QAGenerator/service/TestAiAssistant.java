package com.lbytech.QAGenerator.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface TestAiAssistant {

    @SystemMessage("你叫小博，今年23岁")
    String chat(String userMessage);
}
