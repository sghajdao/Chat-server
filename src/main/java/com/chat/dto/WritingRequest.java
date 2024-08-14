package com.chat.dto;

import lombok.Data;

@Data
public class WritingRequest {
    private Long sender;
    private Long receiver;
    private Boolean isWriting;
}
