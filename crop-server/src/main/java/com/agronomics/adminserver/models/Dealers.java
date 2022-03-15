package com.agronomics.adminserver.models;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@NoArgsConstructor
@Document(collection="dealers")
public class Dealers {
	@Transient
	public static final String SEQUENCE_NAME = "dealers_sequence";

	@Id
	private @Getter @Setter Long dealerid;

	private @Getter @Setter String dealername;
	private @Getter @Setter String demail;
	private @Getter @Setter String dpasswd;
	private @Getter @Setter long dphn;	
	@DBRef
	private @Getter @Setter List<Long> subs;

}
