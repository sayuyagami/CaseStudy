package com.agronomics.adminserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agronomics.adminserver.models.Cropsdata;

@Repository
public interface CropsdataRepository  extends MongoRepository<Cropsdata,Long>{

	Cropsdata findBycropname(String crp);
}
