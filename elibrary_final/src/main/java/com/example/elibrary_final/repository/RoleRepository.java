package com.example.elibrary_final.repository;

import com.example.elibrary_final.model.ERole;
import com.example.elibrary_final.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
