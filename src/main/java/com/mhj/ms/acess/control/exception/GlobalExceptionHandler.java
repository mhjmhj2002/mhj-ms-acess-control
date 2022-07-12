package com.mhj.ms.acess.control.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<Object> erroTratadoException(CustomException customException) {
		log.error("Erro tratado: HttpStatus: {}, Mensagem: {}.", customException.getHttpStatus(), customException.getMessage());
		return new ResponseEntity<Object>(customException.getMessage(), customException.getHttpStatus());
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> erroNaoTratadoException(Exception exception) {
		log.error("Erro nao tratado: ", exception);
		return new ResponseEntity<Object>("Erro desconhecido, contatar administrador do sistema.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
