package com.agronomics.farmersserver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agronomics.farmersserver.models.CropImages;
import com.agronomics.farmersserver.models.Farmers;

@Repository
public interface CropImagesRepository extends MongoRepository<CropImages, String> {

	}