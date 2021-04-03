package com.intercorp.challenge.model.http;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "CustomerWithDeathsResponse", description = "Lista de cliente con fecha probable de muerte")
public class CustomerWithDeathsResponse extends CustomerDTO {

	@ApiModelProperty(value = "fecha probable de muerte", required = true, example = "24/12/2030")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate deathDate;

	public CustomerWithDeathsResponse(int dni, String name, String lastName, int age, LocalDate birthDate,
			LocalDate deathDate) {
		super(dni, name, lastName, age, birthDate);
		this.deathDate = deathDate;
	}

}
