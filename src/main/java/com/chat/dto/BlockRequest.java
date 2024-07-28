package com.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockRequest {
    private Long user;
    private Long block;
}
