package com.lbytech.QAGenerator.config;

import com.lbytech.QAGenerator.repository.MySQLChatMemoryStore;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ai服务配置类
 */
@Configuration
public class AssistantConfig {

    @Autowired
    private MySQLChatMemoryStore mySQLChatmemoryStore;

    @Bean
    // 构建ChatMemoryProvider对象，前端传memoryId，langchain4j根据memoryId匹配话记忆对象，没匹配到就会用get方法来获取chatMemory
    public ChatMemoryProvider chatMemoryProvider() {
        ChatMemoryProvider chatMemoryProvider = new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(8)
                        .chatMemoryStore(mySQLChatmemoryStore)
                        .build();
            }
        };
        return chatMemoryProvider;
    }
}
