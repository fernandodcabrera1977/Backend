package com.intercorp.challenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intercorp.challenge.model.Client;
import com.intercorp.challenge.repository.ClientRepository;
import com.intercorp.challenge.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public List<Client> findAllClients() {
		return clientRepository.findAll();
	}
	
	@Override
	public Client createClient(Client client){
		return clientRepository.save(client);
	}

}
