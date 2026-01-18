package com.lbytech.QAGenerator.config;

import com.lbytech.QAGenerator.properties.PGEmbeddingStoreProperties;
import com.lbytech.QAGenerator.repository.MySQLChatMemoryStore;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
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

    @Autowired
    private PGEmbeddingStoreProperties embeddingStoreProperties;

    @Autowired
    private EmbeddingModel embeddingModel;

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

    /**
     * 构建向量数据库操作对象
     *
     * @return embeddingStore
     */
    @Bean
    public EmbeddingStore embeddingStore() {

        // 构建向量数据库操作对象
        EmbeddingStore embeddingStore = PgVectorEmbeddingStore.builder()
                .host(embeddingStoreProperties.getHost())
                .port(embeddingStoreProperties.getPort())
                .database(embeddingStoreProperties.getDatabase())
                .user(embeddingStoreProperties.getUsername())
                .password(embeddingStoreProperties.getPassword())
                .table(embeddingStoreProperties.getTable())
                .dimension(embeddingModel.dimension()) // 向量维度
                .build();

        return embeddingStore;
    }

    /**
     * 构建向量数据库检索对象
     *
     * @param embeddingStore 向量数据库操作对象
     * @return contentRetriever
     */
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore embeddingStore) {   // spring里要用IOC容器内的对象可以直接声明
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .minScore(0.5)  // 最小的可选入的预选相似度值
                .maxResults(3)  // 最多可查询出的片段
                .embeddingModel(embeddingModel)
                .build();
    }
}
