package com.agronomics.farmersserver.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection="dealers")
public class Dealers {
	@Transient
	public static final String SEQUENCE_NAME = "dealers_sequence";

	@Id
	private @Getter @Setter Long dealerid;
	private @Getter @Setter String dealername,demail,dpasswd;
	private @Getter @Setter long dphn;	
	private @Getter @Setter String role;
}
