package com.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.dto.MessageRequest;
import com.chat.entities.Conversation;
import com.chat.entities.Message;
import com.chat.entities.User;
import com.chat.repositories.ConversationRepository;
import com.chat.repositories.MessageRepository;
import com.chat.repositories.UserRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    public Message saveMessage(MessageRequest request) {
        User s = userRepository.findById(request.getSender()).orElse(null);
        User r = userRepository.findById(request.getReciever()).orElse(null);
        if (r != null && s != null) {
            Message message = Message.builder().content(request.getBody()).sender(s).receiver(r).build();
            if (request.getConversation() != -1) {
                Conversation conversation = conversationRepository.findById(request.getConversation()).orElse(null);
                message.setConversation(conversation);
            } else {
                Conversation conversation = new Conversation();
                conversation = conversationRepository.save(conversation);
                message.setConversation(conversation);
            }
            return messageRepository.save(message);
        }
        return null;
    }
}
