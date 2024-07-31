package com.chat.dto;

import com.chat.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlockResponse {
    private User user;
    private User blocked;
}
