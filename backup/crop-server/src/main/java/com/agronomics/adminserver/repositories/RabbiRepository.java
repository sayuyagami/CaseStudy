package com.agronomics.adminserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agronomics.adminserver.models.RabbiCrops;

@Repository
public interface RabbiRepository extends MongoRepository<RabbiCrops,Long>{

}