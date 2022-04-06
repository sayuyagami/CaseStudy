package com.agronomics.farmersserver.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Document(collection="farmers")
public class Farmers {
@Transient
public static final String SEQUENCE_NAME = "users_sequence";
@Id
private @Getter @Setter long farmerid;
@NotBlank(message = "Name may not contain blanks")
@NotNull(message = "Name may not be null")
@NotEmpty(message = "Name may not be empty")
@Size(min=8,max=20,message="Username must have atleast {min}characters and should not cross to {max} characters")
private @Getter @Setter String farmername;
@NotNull
@NotEmpty
@Email
private @Getter @Setter String femail;
@NotNull
@NotEmpty
@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
private @Getter @Setter String fpasswd;
@Pattern(regexp="^[6-9]\\d{9}$")
private @Getter @Setter long fphn;
@DBRef
private @Getter @Setter Set<Role> roles;
private @Getter @Setter List<Long> subs;
private @Getter @Setter int postsnum;
}
