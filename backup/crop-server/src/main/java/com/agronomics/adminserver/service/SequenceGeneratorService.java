package com.agronomics.adminserver.service;

import static org.springframework.data.mongodb.core.query.Query.query;
import java.util.Objects;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.agronomics.adminserver.models.CropidSequence;

import static org.springframework.data.mongodb.core.query.Criteria.where;
@Service
public class SequenceGeneratorService {


	@Autowired
   private  MongoOperations mongoOperations;

   @Autowired
   public SequenceGeneratorService(MongoOperations mongoOperations) {
       this.mongoOperations = mongoOperations;
   }

   public  long generateSequenceforcrop(String seqcrop) {

	   CropidSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqcrop)),
               new Update().inc("cropseq",1), options().returnNew(true).upsert(true),
               CropidSequence.class);
       return !Objects.isNull(counter) ? counter.getCropseq() : 1;

   }
   	
}
