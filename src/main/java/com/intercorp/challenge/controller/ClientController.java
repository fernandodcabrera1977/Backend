package com.intercorp.challenge.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intercorp.challenge.model.entities.Client;
import com.intercorp.challenge.model.http.ClientDTO;
import com.intercorp.challenge.model.http.ClientsWithDeathsResponse;
import com.intercorp.challenge.model.http.StaticsResponse;
import com.intercorp.challenge.service.ClientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static com.intercorp.challenge.utility.Constants.*;

@Api(value = "Api para desafio intercorp")
@RestController
@RequestMapping(value = "/clientes")
public class ClientController {

	final static Logger logger = LogManager.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private ModelMapper modelmapper;
	
	@PostMapping(path = "/creaCliente")
	@ApiOperation(value = "crea un cliente")
	@ApiResponses({ @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MSJ),
		@ApiResponse(code = SERVER_ERROR, message = SERVER_ERROR_MSJ) })
	public ResponseEntity<String> createClient(@Valid @RequestBody ClientDTO clientDTO) {
		logger.info("llamada al método creaCliente");
		Client responseClient;

		try {
			responseClient = clientService.createClient(modelmapper.map(clientDTO, Client.class));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body("Id cliente creado: " + responseClient.getDni());
	}

	@GetMapping(path = "/kpideclientes")
	@ApiOperation(value = "devuelve edad promedio y desviación estandar")
	@ApiResponses({ @ApiResponse(code = STATUS_OK, message = STATUS_OK_MSJ), @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MSJ),
		@ApiResponse(code = SERVER_ERROR, message = SERVER_ERROR_MSJ) })
	public ResponseEntity getAverageAndStandarDeviation() {
		logger.info("llamada al método getAverageAndStandarDeviation");
		StaticsResponse staticsResponse;

		try {
			staticsResponse = clientService.getStaticsResponse();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(staticsResponse);
	}

	@GetMapping(path = "/listclientes")
	@ApiOperation(value = "devuelve todos los clientes con fecha de muerte probable")
	@ApiResponses({ @ApiResponse(code = STATUS_OK, message = STATUS_OK_MSJ), @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MSJ),
			@ApiResponse(code = SERVER_ERROR, message = SERVER_ERROR_MSJ) })
	public ResponseEntity getClientsWithDeathDate() {
		logger.info("llamada al método getClientsWithDeathDate");
		List<ClientsWithDeathsResponse> clientsWithDeathsResponse;

		try {
			clientsWithDeathsResponse = clientService.getAllClients();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(clientsWithDeathsResponse);
	}

}
