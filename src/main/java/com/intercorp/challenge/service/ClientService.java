package com.intercorp.challenge.service;

import java.util.List;

import com.intercorp.challenge.model.Client;

public interface ClientService {
	
	List<Client>findAllClients();
	Client createClient(Client client);

}
