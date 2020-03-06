package com.bridgelabz.fandoonotesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fandoonotesapi.message.MessageData;
import com.bridgelabz.fandoonotesapi.responce.Response;

@ControllerAdvice
public class GlobalException {
	MessageData message;
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<Response> InvalidTokenException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<Response> InvalidPasswordException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
}
