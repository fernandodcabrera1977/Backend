package com.intercorp.challenge.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataExistException extends RuntimeException{
	private static final long serialVersionUID = -8523144402654050932L;	

	private final String errorMessage;	

}
