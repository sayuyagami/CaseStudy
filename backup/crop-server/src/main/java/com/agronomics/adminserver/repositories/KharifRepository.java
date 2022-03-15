package com.agronomics.adminserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agronomics.adminserver.models.KharifCrops;

@Repository
public interface KharifRepository  extends MongoRepository<KharifCrops,Long>{

}
