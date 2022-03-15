package com.agronomics.Dealersserver.services;

import static org.springframework.data.mongodb.core.query.Query.query;
import java.util.Objects;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.agronomics.Dealersserver.models.DealersSequence;

import static org.springframework.data.mongodb.core.query.Criteria.where;
@Service
public class SequenceGeneratorService {


	@Autowired
   private  MongoOperations mongoOperations;

   @Autowired
   public SequenceGeneratorService(MongoOperations mongoOperations) {
       this.mongoOperations = mongoOperations;
   }

   public  long generateSequencefordealers(String seqName) {

	   DealersSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
               new Update().inc("seq",1), options().returnNew(true).upsert(true),
               DealersSequence.class);
       return !Objects.isNull(counter) ? counter.getSeq() : 1;

   }
   
 
}
