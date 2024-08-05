package com.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyEmailRequest {
    private String email;
    private Integer number;
}
