package com.agronomics.Dealersserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agronomics.Dealersserver.models.Role;


public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}