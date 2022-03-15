package com.agronomics.farmersserver.services;

import static org.springframework.data.mongodb.core.query.Query.query;
import java.util.Objects;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.agronomics.farmersserver.models.FarmersSequence;

import static org.springframework.data.mongodb.core.query.Criteria.where;
@Service
public class SequenceGeneratorService {


	@Autowired
   private  MongoOperations mongoOperations;

   @Autowired
   public SequenceGeneratorService(MongoOperations mongoOperations) {
       this.mongoOperations = mongoOperations;
   }

   public  long generateSequenceforfarmer(String seqName) {

	   FarmersSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
               new Update().inc("seq",1), options().returnNew(true).upsert(true),
               FarmersSequence.class);
       return !Objects.isNull(counter) ? counter.getSeq() : 1;

   }

	
}
