package com.intercorp.challenge.model.http;

import java.util.List;

import com.intercorp.challenge.model.entities.Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "StaticsResponse", description = "Promedio de edad y desviación estandar")
public class StaticsResponse {

	@ApiModelProperty(value = "average")
	private double average;
	@ApiModelProperty(value = "standarDeviation")
	private double standarDeviation;
	
	public StaticsResponse(List<Client> clients) {
		this.average = resolveAverage(clients);
		this.standarDeviation = resolveStandarDeviation(clients, average);
	}

	private double resolveAverage(List<Client> clients) {
		return clients.stream().mapToInt(Client::getAge).average().getAsDouble();
	}

	private double resolveStandarDeviation(List<Client> clients, double average) {
		double variance = clients.stream().map(i -> i.getAge() - average).map(i -> i * i).mapToDouble(i -> i).average()
				.getAsDouble();
		return Math.sqrt(variance);
	}
}
