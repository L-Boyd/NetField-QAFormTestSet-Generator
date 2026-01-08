package com.lbytech.QAGenerator.repository;

import com.lbytech.QAGenerator.entity.po.ChatMemoryPO;
import com.lbytech.QAGenerator.service.IMySQLChatMemoryService;
import dev.langchain4j.data.message.*;
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
        ChatMemoryPO chatMemoryPO = mySQLChatMemoryService.lambdaQuery()
                .eq(ChatMemoryPO::getSessionId, sessionId)
                .one();
        if (chatMemoryPO == null) {
            return List.of();
        }
        String content = chatMemoryPO.getContent();

        List<ChatMessage> chatMessages = ChatMessageDeserializer.messagesFromJson(content);
        return chatMessages;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String sessionId = memoryId.toString();

        // 先删除
        mySQLChatMemoryService.lambdaUpdate()
                .eq(ChatMemoryPO::getSessionId, sessionId)
                .remove();

        // 再保存
        String json = ChatMessageSerializer.messagesToJson(messages);
        ChatMemoryPO chatMemoryPO = new ChatMemoryPO();
        chatMemoryPO.setSessionId(sessionId);
        chatMemoryPO.setContent(json);
        mySQLChatMemoryService.save(chatMemoryPO);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        String sessionId = memoryId.toString();
        mySQLChatMemoryService.lambdaUpdate()
                .eq(ChatMemoryPO::getSessionId, sessionId)
                .remove();
    }
}
