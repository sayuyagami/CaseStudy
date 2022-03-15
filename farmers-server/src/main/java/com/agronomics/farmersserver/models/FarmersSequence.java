package com.agronomics.farmersserver.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "farmers_sequences")
@Component
public class FarmersSequence {

	@Id
	private @Getter @Setter String id;
    private @Getter @Setter long seq;

}



