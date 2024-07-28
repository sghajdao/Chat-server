package com.chat.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.dto.MessageRequest;
import com.chat.entities.Conversation;
import com.chat.entities.Message;
import com.chat.entities.User;
import com.chat.repositories.ConversationRepository;
import com.chat.repositories.MessageRepository;
import com.chat.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    @Transactional
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
            if (s.getContacts() == null || !s.getContacts().contains(r)) {
                s.getContacts().size();
                s.getContacts().add(r);
                userRepository.save(s);
            }
            if (r.getContacts() == null || !r.getContacts().contains(s)) {
                r.getContacts().size();
                r.getContacts().add(s);
                userRepository.save(r);
            }
            message.setCreatedAt(new Date());
            return messageRepository.save(message);
        }
        return null;
    }
}
