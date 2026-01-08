package com.lbytech.QAGenerator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbytech.QAGenerator.entity.po.ChatMemoryPO;
import com.lbytech.QAGenerator.mapper.MySQLChatMemoryMapper;
import com.lbytech.QAGenerator.service.IMySQLChatMemoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MySQLChatMemoryServiceImpl extends ServiceImpl<MySQLChatMemoryMapper, ChatMemoryPO> implements IMySQLChatMemoryService {

}
