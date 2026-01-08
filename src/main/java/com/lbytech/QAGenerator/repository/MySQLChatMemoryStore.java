package com.lbytech.QAGenerator.repository;

import com.lbytech.QAGenerator.entity.po.ChatMemoryPO;
import com.lbytech.QAGenerator.service.IMySQLChatMemoryService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将对话记忆存储到MySQL数据库
 */
@Repository
public class MySQLChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private IMySQLChatMemoryService mySQLChatMemoryService;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String sessionId = (String) memoryId;
        List<ChatMemoryPO> chatMemoryPOList = mySQLChatMemoryService.getMessagesBySessionId(sessionId);

        // 将ChatMemoryPO列表转换为ChatMessage列表
        return chatMemoryPOList.stream()
                .map(this::convertChatMemoryPOToChatMessage)
                .toList();
    }

    /**
     * 将ChatMemory实体转换为ChatMessage
     */
    private ChatMessage convertChatMemoryPOToChatMessage(ChatMemoryPO chatMemory) {
        String type = chatMemory.getMessageType();
        String content = chatMemory.getContent();

        return switch (type) {
            case "USER" -> UserMessage.from(content);
            case "AI" -> AiMessage.from(content);
            case "SYSTEM" -> SystemMessage.from(content);
            default -> throw new IllegalArgumentException("Unknown message type: " + type);
        };
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String sessionId = memoryId.toString();

        // 先删除该会话的所有旧消息
        mySQLChatMemoryService.lambdaUpdate()
                .eq(ChatMemoryPO::getSessionId, sessionId)
                .remove();

        // 保存新消息
        List<ChatMemoryPO> chatMemories = messages.stream()
                .map(msg -> convertToChatMemory(sessionId, msg))
                .collect(Collectors.toList());
        mySQLChatMemoryService.saveBatch(chatMemories);
    }

    /**
     * 将ChatMessage转换为ChatMemoryPO实体
     */
    private ChatMemoryPO convertToChatMemory(String sessionId, ChatMessage message) {
        ChatMemoryPO chatMemory = new ChatMemoryPO();
        chatMemory.setSessionId(sessionId);
        chatMemory.setMessageType(message.type().name());
        chatMemory.setContent(message.toString());
        chatMemory.setTime(LocalDateTime.now());

        return chatMemory;
    }

    @Override
    public void deleteMessages(Object memoryId) {
        String sessionId = memoryId.toString();
        mySQLChatMemoryService.lambdaUpdate()
                .eq(ChatMemoryPO::getSessionId, sessionId)
                .remove();
    }
}
