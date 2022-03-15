package com.agronomics.Dealersserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agronomics.Dealersserver.models.Dealers;


@Repository
public interface DealersRepository extends MongoRepository<Dealers,Long>{

	Dealers findBydemail(String demail);
	Dealers findBydealername(String dealername);
}
