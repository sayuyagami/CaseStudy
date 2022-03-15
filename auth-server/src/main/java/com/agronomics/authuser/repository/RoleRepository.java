package com.agronomics.authuser.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agronomics.authuser.models.ERole;
import com.agronomics.authuser.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
