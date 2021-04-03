package com.intercorp.challenge.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intercorp.challenge.model.entities.Customer;
import com.intercorp.challenge.model.http.CustomerDTO;
import com.intercorp.challenge.model.http.CustomerWithDeathsResponse;
import com.intercorp.challenge.model.http.StaticsResponse;
import com.intercorp.challenge.service.CustomerService;

import org.springframework.http.MediaType;

import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.hasSize;;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CustomerService service;
	
    @Autowired
    ObjectMapper objectMapper;

	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setAge(42);
		customer.setDni(28999000);
		customer.setLastName("Bard");
		customer.setName("Luis");
		customer.setBirthDate(LocalDate.of(1980, 3, 23));

		return customer;
	}

	@Test
	void createClientTest() throws Exception {

		when(service.createCustomer(any())).thenReturn(getCustomer());

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAge(20);
		customerDTO.setBirthDate(LocalDate.of(2001, 6, 5));
		customerDTO.setDni(40000444);
		customerDTO.setName("Luciana");
		customerDTO.setLastName("Ros");
		String customerDTOJson = objectMapper.writeValueAsString(customerDTO);

		mvc.perform(post("/clientes/creaCliente").contentType(MediaType.APPLICATION_JSON).content(customerDTOJson))
				.andExpect(status().isCreated());
	}
	
	@Test
	void createClientBadRequestTest() throws Exception {

		when(service.createCustomer(any())).thenReturn(getCustomer());

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAge(0);
		customerDTO.setBirthDate(LocalDate.of(2001, 6, 5));
		customerDTO.setDni(40000444);
		customerDTO.setName("Luciana");
		customerDTO.setLastName("Ros");
		String customerDTOJson = objectMapper.writeValueAsString(customerDTO);

		mvc.perform(post("/clientes/creaCliente").contentType(MediaType.APPLICATION_JSON).content(customerDTOJson))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	void createClientExceptionTest() throws Exception {

		when(service.createCustomer(any())).thenThrow(RuntimeException.class);

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAge(20);
		customerDTO.setBirthDate(LocalDate.of(2001, 6, 5));
		customerDTO.setDni(40000444);
		customerDTO.setName("Luciana");
		customerDTO.setLastName("Ros");
		String customerDTOJson = objectMapper.writeValueAsString(customerDTO);

		mvc.perform(post("/clientes/creaCliente").contentType(MediaType.APPLICATION_JSON).content(customerDTOJson))
				.andExpect(status().is5xxServerError());
	}

	@Test
	void getAverageAndStandarDeviationTest() throws Exception {

		List<Customer> customers = Arrays.asList(getCustomer());

		StaticsResponse staticsResponse = new StaticsResponse(customers);

		when(service.getStaticsResponse()).thenReturn(staticsResponse);

		mvc.perform(get("/clientes/kpideclientes").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getCustomersWithDeathDateTest() throws Exception {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		CustomerWithDeathsResponse customer = new CustomerWithDeathsResponse(30999888, "Ivan", "Tevez", 37,
				LocalDate.of(1983, 1, 1), LocalDate.of(2055, 1, 1));

		List<CustomerWithDeathsResponse> customers = Arrays.asList(customer);

		when(service.getAllCustomers()).thenReturn(customers);

		mvc.perform(get("/clientes/listclientes").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is(customer.getName())))
				.andExpect(jsonPath("$[0].dni", is(customer.getDni())))
				.andExpect(jsonPath("$[0].lastName", is(customer.getLastName())))
				.andExpect(jsonPath("$[0].age", is(customer.getAge())))
				.andExpect(jsonPath("$[0].birthDate".toString(), is(formatter.format(customer.getBirthDate()))))
				.andExpect(jsonPath("$[0].deathDate".toString(), is(formatter.format(customer.getDeathDate()))));
	}

}
