package com.agronomics.farmersserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agronomics.farmersserver.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}