package com.agronomics.Dealersserver.models;

import java.util.Set;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;


@Entity
@Document(collection = "roles")
public class Role {

	@Id
   private @Getter @Setter String id;
   @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
   
   private @Getter @Setter String role;
}