package com.lbytech.QAGenerator.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService(
        streamingChatModel = "openaiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface GeneratorAssistant {

    @SystemMessage(fromResource = "static/system.txt")
    Flux<String> chatByStream(@MemoryId String id, @UserMessage String userMessage);
}
