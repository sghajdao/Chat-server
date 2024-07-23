package com.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    // @MessageMapping("/hello")
    // @SendTo("/topic/public")
    // public Message sendMessage(
    // @Payload Message message) {
    // return message;
    // }

    // @MessageMapping("/chat.addUser")
    // @SendTo("/topic/public")
    // public Message addUser(
    // @Payload Message message,
    // SimpMessageHeaderAccessor headerAccessor) {
    // // Add username in web socket session
    // headerAccessor.getSessionAttributes().put("username", message.getSender());
    // return message;
    // }

    private SimpMessagingTemplate template;

    @Autowired
    void WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/public")
    public void greeting(String message) throws Exception {
        this.template.convertAndSend("/message", message);
    }
}
