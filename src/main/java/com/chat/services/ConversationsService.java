package com.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.dto.ConversationRequest;
import com.chat.entities.Conversation;
import com.chat.repositories.ConversationRepository;

@Service
public class ConversationsService {
    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation getConversationById(Long id) {
        Conversation conversation = conversationRepository.findById(id).orElse(null);
        return conversation;
    }

    public Conversation getConversationBysr(ConversationRequest request) {
        Conversation conversation = conversationRepository
                .findConversationBySenderAndReceiver(request.getSender(), request.getReceiver()).orElse(null);
        return conversation;
    }
}
