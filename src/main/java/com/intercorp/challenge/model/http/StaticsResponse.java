package com.intercorp.challenge.model.http;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.intercorp.challenge.model.entities.Customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "StaticsResponse", description = "Promedio de edad y desviación estandar")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaticsResponse {

	@ApiModelProperty(value = "average")
	private double mean;
	@ApiModelProperty(value = "standarDeviation")
	private double standarDeviation;

	public StaticsResponse(List<Customer> customers) {
		this.mean = calculateMean(customers);
		this.standarDeviation = calculateStandarDeviation(customers, mean);
	}

	private double calculateMean(List<Customer> customers) {
		return customers.stream().mapToInt(Customer::getAge).average().getAsDouble();
	}

	private double calculateStandarDeviation(List<Customer> customers, double average) {
		double variance = customers.stream().map(i -> i.getAge() - average).map(i -> i * i).mapToDouble(i -> i)
				.average().getAsDouble();
		return Math.sqrt(variance);
	}
}
