package com.example.elibrary_final.security.sec_service;

import com.example.elibrary_final.model.User;
import com.example.elibrary_final.repository.UserRepository;
import com.example.elibrary_final.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

    @Service
    public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
        @Autowired
        UserRepository userRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
            return UserDetailsImpl.build(user);
        }
    }



