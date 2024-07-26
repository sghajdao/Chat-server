package com.chat.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String body;
    private Long sender;
    private Long reciever;
    private Long conversation;
}
