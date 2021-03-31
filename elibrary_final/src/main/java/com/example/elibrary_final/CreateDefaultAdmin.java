package com.example.elibrary_final;

import com.example.elibrary_final.controller.AuthorController;
import com.example.elibrary_final.controller.BookController;
import com.example.elibrary_final.controller.GenreController;
import com.example.elibrary_final.controller.UserController;
import com.example.elibrary_final.model.Author;
import com.example.elibrary_final.model.Book;
import com.example.elibrary_final.model.Genre;
import com.example.elibrary_final.model.Role;
import com.example.elibrary_final.service.SignUpReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component

public class CreateDefaultAdmin implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    UserController userController;
    @Autowired
    BookController bookController;
    @Autowired
    GenreController genreController;
    @Autowired
    AuthorController authorController;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        SignUpReq signUpReq = new SignUpReq();
        signUpReq.setUsername("admin");
        signUpReq.setPassword("password");
        signUpReq.setRoles(roles);
        userController.signup(signUpReq);
    }
}
