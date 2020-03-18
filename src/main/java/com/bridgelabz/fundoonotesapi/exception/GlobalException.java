package com.bridgelabz.fundoonotesapi.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonotesapi.message.MessageData;
import com.bridgelabz.fundoonotesapi.responce.Response;
/**
 * @author :- Krunal Parate
 * Purpose :- Global Exception
 */
@ControllerAdvice
public class GlobalException {
	@Autowired
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
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<Response> InvalidUserException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidNoteException.class)
	public ResponseEntity<Response> InvalidNoteException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(UserAlreadyPresentException.class)
	public ResponseEntity<Response> UserAlreadyPresentException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidOrderException.class)
	public ResponseEntity<Response> InvalidOrderException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Redirect_Request)
				,e.getMessage(),"Enter the asc or desc"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidLabelException.class)
	public ResponseEntity<Response> InvalidLabelException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidCollabratorException.class)
	public ResponseEntity<Response> InvalidCollabratorException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ReceiverMailAlreadyPresentException.class)
	public ResponseEntity<Response> ReceiverMailAlreadyPresentException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(FileNotUploadedException.class)
	public ResponseEntity<Response> FileNotUploadedException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(FileIsEmpty.class)
	public ResponseEntity<Response> FileIsEmpty(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidReminderException.class)
	public ResponseEntity<Response> InvalidReminderException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ReminderAlreadyPresentException.class)
	public ResponseEntity<Response> ReminderAlreadyPresentException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(LabelAlreadyPresentException.class)
	public ResponseEntity<Response> LabelAlreadyPresentException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidTitleException.class)
	public ResponseEntity<Response> InvalidTitleException(Exception e){
		return new ResponseEntity<Response>(new Response(Integer.parseInt(message.Bad_Request)
				,e.getMessage(),"Please Try Again"),HttpStatus.BAD_REQUEST);
	}
}
