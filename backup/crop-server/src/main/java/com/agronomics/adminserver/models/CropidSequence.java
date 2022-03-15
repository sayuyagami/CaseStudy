package com.agronomics.adminserver.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "crop_sequences")
@Component
public class CropidSequence {

    @Id
	private @Getter @Setter String id;
    private @Getter @Setter long cropseq;
}



