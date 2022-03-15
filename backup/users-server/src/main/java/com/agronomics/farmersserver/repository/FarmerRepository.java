package com.agronomics.farmersserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.agronomics.farmersserver.models.Farmers;

@Repository
public interface FarmerRepository extends MongoRepository<Farmers,Long>{
	
	
	//@Query("{$and:[{'_id':?0},{'fcrops.cropid': ?1}]},{'fcrops.$':1}")
	//List<Farmers> findcropbyid(Long id, int cropid);

	Farmers findByfemail(String femail);

	Farmers findByfarmername(String farmernm);

	//void save(Crops crops);

	//void deleteById(Crops cbyid);
	
	//@Aggregation(pipeline = {"{'$project':{'_id':0,'fcrops':1,farmername:1}}"})
	//List<Farmers> findAllcrops();

}

