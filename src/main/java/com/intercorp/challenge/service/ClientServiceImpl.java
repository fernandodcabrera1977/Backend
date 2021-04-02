package com.intercorp.challenge.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intercorp.challenge.model.entities.Client;
import com.intercorp.challenge.model.http.ClientsWithDeathsResponse;
import com.intercorp.challenge.model.http.StaticsResponse;
import com.intercorp.challenge.repository.ClientRepository;
import com.intercorp.challenge.utility.Constants;

@Service
public class ClientServiceImpl implements ClientService {
	
	final static Logger logger = LogManager.getLogger(ClientService.class);
	
	@Autowired
	private ClientRepository clientRepository;

	
	@Override
	public Client createClient(Client client) {
		logger.info("llamada al método crear cliente");
		try {
			return clientRepository.save(client);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public StaticsResponse getStaticsResponse() {
		logger.info("llamada al método getStaticsResponse");
		StaticsResponse response = null;
		List<Client> clients = findAll();

		if (clients.size() > 0) {
			response = new StaticsResponse(clients);
		}
		return response;
	}

	@Override
	public List<ClientsWithDeathsResponse> getAllClients() {
		logger.info("llamada al método getAllClients");
		
		return findAll()
				.stream().map(c -> new ClientsWithDeathsResponse(c.getDni(), c.getName(), c.getLastName(), c.getAge(),
						c.getBirthDate(), c.getBirthDate().plusYears(Constants.GLOBAL_LIVE_EXPECTATIVE)))
				.collect(Collectors.toList());
	}
	
	private List<Client> findAll() {
		List<Client> clients;
		try {
			clients = clientRepository.findAll();			
		} catch (Exception e) {
			throw e;
		}
		return clients;
	}

}
