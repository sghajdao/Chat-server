package com.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chat.dto.BlockRequest;
import com.chat.dto.MessageRequest;
import com.chat.entities.Message;
import com.chat.entities.User;
import com.chat.repositories.UserRepository;
import com.chat.services.MessageService;

@Controller
public class ChatController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/hello")
    @SendTo("/topic/public")
    public void greeting(MessageRequest request) throws Exception {
        Message message = messageService.saveMessage(request);
        this.template.convertAndSend("/message" + request.getSender().toString(), message);
        this.template.convertAndSend("/message" + request.getReciever().toString(),
                message);
    }

    @MessageMapping("/block")
    @SendTo("/topic/public")
    public void updateUser(BlockRequest request) throws Exception {
        User user = userRepository.findById(request.getUser()).orElse(null);
        this.template.convertAndSend("/user" + request.getBlock().toString(), user);
    }
}
