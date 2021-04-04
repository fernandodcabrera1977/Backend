package com.intercorp.challenge.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intercorp.challenge.exceptions.DataExistException;
import com.intercorp.challenge.model.entities.Customer;
import com.intercorp.challenge.model.http.CustomerWithDeathsResponse;
import com.intercorp.challenge.model.http.StaticsResponse;
import com.intercorp.challenge.repository.CustomerRepository;
import com.intercorp.challenge.utility.Constants;

@Service
public class CustomerServiceImpl implements CustomerService {
	static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);
	
	private CustomerRepository customerRepository;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		logger.info("llamada al método crear cliente");
		try {

			Optional<Customer> customerExist = customerRepository.findById(customer.getDni());

			if (customerExist.isPresent()) {
				throw new DataExistException("Cliente dni: " + customer.getDni() + " ya existe.");
			}
			return customerRepository.save(customer);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public StaticsResponse getStaticsResponse() {
		logger.info("llamada al método getStaticsResponse");
		StaticsResponse response = null;
		List<Customer> customers = customerRepository.findAll();

		if (!customers.isEmpty()) {
			response = new StaticsResponse(customers);
		}
		return response;
	}

	@Override
	public List<CustomerWithDeathsResponse> getAllCustomers() {
		logger.info("llamada al método getAllCustomers");

		return customerRepository.findAll().stream()
				.map(c -> new CustomerWithDeathsResponse(c.getDni(), c.getName(), c.getLastName(), c.getAge(),
						c.getBirthDate(), c.getBirthDate().plusYears(Constants.GLOBAL_LIVE_EXPECTATIVE)))
				.collect(Collectors.toList());
	}

}
