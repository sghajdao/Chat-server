package com.chat.services;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.dto.BlockRequest;
import com.chat.dto.ConversationRequest;
import com.chat.entities.User;
import com.chat.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    public User editUser(User user) {
        User old = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (old != null) {
            old.setFirstname(user.getFirstname());
            old.setLastname(user.getLastname());
            old.setImage(user.getImage());
            return userRepository.save(old);
        }
        return null;
    }

    public Collection<User> getContacts(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return null;
        return user.getContacts();
    }

    public User addContact(ConversationRequest request) {
        User sender = userRepository.findById(request.getSender()).orElse(null);
        User receiver = userRepository.findById(request.getReceiver()).orElse(null);
        if (sender == null || receiver == null)
            return null;
        sender.getContacts().size();
        sender.getContacts().add(receiver);
        receiver.getContacts().size();
        receiver.getContacts().add(sender);
        userRepository.save(receiver);
        return userRepository.save(sender);
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User blockUser(BlockRequest request) {
        User user = userRepository.findById(request.getUser()).orElse(null);
        if (user == null)
            return null;
        Collection<Long> list = (user.getBlackList() == null) ? new ArrayList<Long>() : user.getBlackList();
        list.add(request.getBlock());
        user.setBlackList(list);
        return userRepository.save(user);
    }

    public User unblockUser(BlockRequest request) {
        User user = userRepository.findById(request.getUser()).orElse(null);
        if (user == null)
            return null;
        Collection<Long> list = user.getBlackList();
        list.remove(Long.valueOf(request.getBlock()));
        user.setBlackList(list);
        return userRepository.save(user);
    }
}
