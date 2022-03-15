package com.agronomics.adminserver.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agronomics.adminserver.models.CropImages;

@Repository
public interface CropImagesRepository  extends MongoRepository<CropImages,Long>{

	//Optional<CropImages> findById(Long id);

}
