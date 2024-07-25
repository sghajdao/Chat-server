package com.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
