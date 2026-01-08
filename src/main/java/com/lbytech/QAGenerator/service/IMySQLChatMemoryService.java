package com.lbytech.QAGenerator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbytech.QAGenerator.entity.po.ChatMemoryPO;

import java.util.List;

public interface IMySQLChatMemoryService extends IService<ChatMemoryPO> {

    /**
     * 根据会话ID获取消息列表
     * @param sessionId 会话ID
     * @return 消息列表
     */
    List<ChatMemoryPO> getMessagesBySessionId(String sessionId);
}
