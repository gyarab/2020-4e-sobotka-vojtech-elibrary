package com.example.elibrary.service;

import com.example.elibrary.model.User;
import com.example.elibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        return null;
    }
}
