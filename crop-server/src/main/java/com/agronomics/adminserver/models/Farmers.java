package com.agronomics.adminserver.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
private @Getter @Setter String farmername;
private @Getter @Setter String femail;
private @Getter @Setter String fpasswd;
private @Getter @Setter long fphn;
private @Getter @Setter String role;

}
