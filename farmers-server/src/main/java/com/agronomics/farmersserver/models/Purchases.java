package com.agronomics.farmersserver.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Document(collection="purchases")
public class Purchases {
@Id
private  @Setter @Getter Long purchaseid;
private @Getter @Setter Long dealerid;
private @Getter @Setter Long reqcropid;
private @Getter @Setter String dealername;
private @Getter @Setter int Negotiateprice;
private @Getter @Setter String reqstatus;
 
}
