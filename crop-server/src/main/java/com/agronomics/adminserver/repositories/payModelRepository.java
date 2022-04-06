package com.agronomics.adminserver.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.agronomics.adminserver.models.payModel;

@Repository
public interface payModelRepository extends MongoRepository<payModel,String>{

	List<payModel> findBycustid(int dealerid);
	List<payModel> findByfid(int farmerid);
}
