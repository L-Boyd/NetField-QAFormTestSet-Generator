package com.lbytech.QAGenerator.entity.dto;

import lombok.Getter;

/**
 * 与大模型chat的请求体
 */
@Getter
public class ChatForm {

    private String id;

    private String message;
}
