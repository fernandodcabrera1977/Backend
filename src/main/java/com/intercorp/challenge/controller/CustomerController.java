package com.intercorp.challenge.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intercorp.challenge.model.entities.Customer;
import com.intercorp.challenge.model.http.CustomerDTO;
import com.intercorp.challenge.model.http.CustomerWithDeathsResponse;
import com.intercorp.challenge.model.http.StaticsResponse;
import com.intercorp.challenge.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static com.intercorp.challenge.utility.Constants.*;

@Api(value = "Api para desafio intercorp")
@RestController
@RequestMapping(value = "/clientes")
public class CustomerController {
	static final Logger logger = LogManager.getLogger(CustomerController.class);

	private CustomerService customerService;
	private ModelMapper modelmapper;

	@Autowired
	public CustomerController(CustomerService customerService, ModelMapper modelmapper) {
		this.customerService = customerService;
		this.modelmapper = modelmapper;
	}

	@PostMapping(path = "/creaCliente")
	@ApiOperation(value = "crea un cliente")
	@ApiResponses({ @ApiResponse(code = SERVER_ERROR, message = SERVER_ERROR_MSJ) })
	public ResponseEntity<String> createClient(@Valid @RequestBody CustomerDTO customerDTO) {
		logger.info("llamada al método creaCliente");

		Customer responseCustomer = customerService.createCustomer(modelmapper.map(customerDTO, Customer.class));

		return ResponseEntity.status(HttpStatus.CREATED).body("Id cliente creado: " + responseCustomer.getDni());
	}

	@GetMapping(path = "/kpideclientes")
	@ApiOperation(value = "devuelve edad promedio y desviación estandar")
	@ApiResponses({ @ApiResponse(code = SERVER_ERROR, message = SERVER_ERROR_MSJ) })
	public ResponseEntity<StaticsResponse> getAverageAndStandarDeviation() {
		logger.info("llamada al método getAverageAndStandarDeviation");

		return ResponseEntity.status(HttpStatus.OK).body(customerService.getStaticsResponse());
	}

	@GetMapping(path = "/listclientes")
	@ApiOperation(value = "devuelve todos los clientes con fecha de muerte probable")
	@ApiResponses({ @ApiResponse(code = SERVER_ERROR, message = SERVER_ERROR_MSJ) })
	public ResponseEntity<List<CustomerWithDeathsResponse>> getCustomersWithDeathDate() {
		logger.info("llamada al método getClientsWithDeathDate");

		return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
	}

}
