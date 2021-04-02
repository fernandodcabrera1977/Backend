package com.intercorp.challenge.service;

import java.util.List;

import com.intercorp.challenge.model.entities.Client;
import com.intercorp.challenge.model.http.ClientsWithDeathsResponse;
import com.intercorp.challenge.model.http.StaticsResponse;

public interface ClientService {

	Client createClient(Client client);

	StaticsResponse getStaticsResponse();
	
	List <ClientsWithDeathsResponse> getAllClients();

}
