package com.agronomics.Dealersserver.models;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.DBRef;
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
	@NotBlank(message = "Name may not contain blanks")
	@NotNull(message = "Name may not be null")
	@NotEmpty(message = "Name may not be empty")
	@Size(min=8,max=20,message="Username must have atleast {min}characters and should not cross to {max} characters")
	private @Getter @Setter String dealername;
	@NotNull
	@NotEmpty
	@Email
	private @Getter @Setter String demail;
	@NotNull
	@NotEmpty
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
	private @Getter @Setter String dpasswd;
	@Pattern(regexp="^[6-9]\\d{9}$")
	private @Getter @Setter long dphn;	
	@DBRef
	private @Getter @Setter Set<Role> roles;
	private @Getter @Setter List<Long> subs;

}
