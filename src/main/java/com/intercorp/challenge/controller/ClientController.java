package com.intercorp.challenge.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intercorp.challenge.model.Client;
import com.intercorp.challenge.model.ClientDTO;
import com.intercorp.challenge.service.ClientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Api para desafio intercorp")
public class ClientController {

	final static Logger logger = LogManager.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ModelMapper modelmapper;

	@PostMapping(path = "/creaCliente")
	@ApiOperation(value = "crea un cliente")
	@ApiResponses({ @ApiResponse(code = 201, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server error") })	
	public ResponseEntity<String> createClient(@Valid @RequestBody ClientDTO clientDTO) {
		Client responseClient;

		try {
			responseClient = clientService.createClient(modelmapper.map(clientDTO, Client.class));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body("Id cliente creado: " + responseClient.getId());
	}

}
