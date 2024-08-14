package com.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.chat.dto.BlockRequest;
import com.chat.dto.BlockResponse;
import com.chat.dto.MessageRequest;
import com.chat.dto.WritingRequest;
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

    @MessageMapping("/write")
    @SendTo("/topic/public")
    public void writing(WritingRequest request) throws Exception {
        if (request.getIsWriting() == true)
            this.template.convertAndSend("/writing" + request.getReceiver().toString(), request);
        else
            this.template.convertAndSend("/writing" + request.getReceiver().toString(), request);
    }

    @MessageMapping("/block")
    @SendTo("/topic/public")
    public void updateUser(BlockRequest request) throws Exception {
        User user = userRepository.findById(request.getUser()).orElse(null);
        User bloked = userRepository.findById(request.getBlock()).orElse(null);
        BlockResponse response = new BlockResponse(user, bloked);
        this.template.convertAndSend("/user" + request.getBlock().toString(), response);
    }
}
