package com.intercorp.challenge.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.intercorp.challenge.model.entities.Customer;
import com.intercorp.challenge.model.http.CustomerWithDeathsResponse;
import com.intercorp.challenge.model.http.StaticsResponse;
import com.intercorp.challenge.repository.CustomerRepository;
import com.intercorp.challenge.utility.Constants;

class CustomerServiceImplTest {

	private CustomerRepository customerRepositoryMock;
	private CustomerService customerService;

	@BeforeEach
	public void setUp() {
		customerRepositoryMock = Mockito.mock(CustomerRepository.class);
		customerService = new CustomerServiceImpl(customerRepositoryMock);
	}

	private List<Customer> getCustomers() {

		List<Customer> customers = new ArrayList<>();

		LocalDate birthDate = LocalDate.of(1984, 12, 12);

		Customer customer = new Customer();
		customer.setAge(36);
		customer.setBirthDate(birthDate);
		customer.setDni(30999000);
		customer.setName("Juan");
		customer.setLastName("Perez");

		customers.add(customer);

		birthDate = LocalDate.of(1991, 11, 11);
		customer = new Customer();
		customer.setAge(30);
		customer.setBirthDate(birthDate);
		customer.setDni(34777888);
		customer.setName("Miguel");
		customer.setLastName("Juarez");

		customers.add(customer);

		birthDate = LocalDate.of(1983, 9, 11);
		customer = new Customer();
		customer.setAge(27);
		customer.setBirthDate(birthDate);
		customer.setDni(38444555);
		customer.setName("Rocio");
		customer.setLastName("Lequizamon");

		customers.add(customer);

		return customers;
	}

	@Test
	void createCustomerTest() {
		Customer customer = new Customer();

		when(customerRepositoryMock.save(customer)).thenReturn(customer);

		customer = customerService.createCustomer(customer);

		assertNotNull(customer);
	}

	@Test
	void getStaticsResponseTest() {
		double tolerance = 1.0E-9;

		when(customerRepositoryMock.findAll()).thenReturn(getCustomers());

		StaticsResponse response = customerService.getStaticsResponse();

		double expectedMean = 31;
		double expectedSD = 3.7416573867739;

		assertEquals(expectedMean, response.getMean(), tolerance);
		assertEquals(expectedSD, response.getStandarDeviation(), tolerance);
	}

	@Test
	void getAllCustomersTest() {

		when(customerRepositoryMock.findAll()).thenReturn(getCustomers());

		List<CustomerWithDeathsResponse> customers = customerService.getAllCustomers();

		Customer c = getCustomers().get(0);
		LocalDate expectedDeathAge = c.getBirthDate().plusYears(Constants.GLOBAL_LIVE_EXPECTATIVE);

		assertEquals(3, customers.size());
		assertEquals(expectedDeathAge, customers.get(0).getDeathDate());
	}
}
