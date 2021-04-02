package com.intercorp.challenge.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Client")
public class Client {
	
	@Id
	private int id;
	private String name;
	private String lastName;
	private int age;
	private LocalDate birthDate;	


}
