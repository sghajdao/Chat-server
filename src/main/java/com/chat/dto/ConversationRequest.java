package com.chat.dto;

import lombok.Data;

@Data
public class ConversationRequest {
    private Long sender;
    private Long receiver;
}
