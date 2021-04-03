package com.intercorp.challenge.service;

import java.util.List;

import com.intercorp.challenge.model.entities.Customer;
import com.intercorp.challenge.model.http.CustomerWithDeathsResponse;
import com.intercorp.challenge.model.http.StaticsResponse;

public interface CustomerService {

	Customer createCustomer(Customer customer);

	StaticsResponse getStaticsResponse();

	List<CustomerWithDeathsResponse> getAllCustomers();

}
