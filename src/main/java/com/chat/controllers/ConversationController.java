package com.chat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.dto.ConversationRequest;
import com.chat.entities.Conversation;
import com.chat.services.ConversationsService;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {
    @Autowired
    private ConversationsService conversationsService;

    @GetMapping("/{id}")
    private ResponseEntity<Conversation> getConversationById(@PathVariable Long id) {
        Conversation conversation = conversationsService.getConversationById(id);
        if (conversation == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }

    @PostMapping("/get")
    private ResponseEntity<Conversation> getConversation(@RequestBody ConversationRequest request) {
        Conversation conversation = conversationsService.getConversationBysr(request);
        if (conversation == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    private ResponseEntity<List<Conversation>> getConversationsByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(conversationsService.getConversationsByUserId(id), HttpStatus.OK);
    }
}
