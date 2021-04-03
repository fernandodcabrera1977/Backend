package com.intercorp.challenge.model.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Customer")
public class Customer {

	@Id
	private int dni;
	private String name;
	private String lastName;
	private int age;
	private LocalDate birthDate;

}
