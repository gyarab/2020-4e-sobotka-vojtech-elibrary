package com.example.elibrary.repository;

import com.example.elibrary.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message, String> {
    Optional<Message> findById(String id);
}
