package com.agronomics.Dealersserver.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
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
private  @Setter @Getter Long dealerid;
private  @Setter @Getter String dealername;
private  @Setter @Getter Long reqcropid;
private @Setter @Getter int Negotiateprice;
private @Setter @Getter String reqstatus;
private @Setter @Getter String croptype;

 
}
