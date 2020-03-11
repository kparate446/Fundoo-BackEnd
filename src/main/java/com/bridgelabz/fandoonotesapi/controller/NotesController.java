package com.bridgelabz.fandoonotesapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fandoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fandoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.service.NoteService;
/**
 * @author :- Krunal Parate
 * Purpose :- Created Add, Update, Delete Notes API
 */
@RestController 
@RequestMapping("/notesapi")
public class NotesController {
	@Autowired // Declaired the Dependency
	private NoteService service;
	/**
	 * Purpose :- Created the Note
	 * @param token :- Get the Token in User
	 * @param createNoteDto 
	 * @return :- Response
	 */
	@PostMapping("/createNote")
	public ResponseEntity<String> createNote(@RequestHeader String token,@Valid @RequestBody CreateNoteDto createNoteDto){
		Response response = service.createNote(token,createNoteDto);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Updated the Note
	 * @param token:- Get the Token in User
	 * @param updateNoteDto
	 * @param id:- Which position Update
	 * @return :-Response
	 */
	@PutMapping("/updateNote/{id}")
	public ResponseEntity<String> UpdateNote(@RequestHeader String token,@Valid @RequestBody UpdateNoteDto updateNoteDto,@PathVariable  int id){
		Response response = service.updateNote(token,updateNoteDto,id);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Deleted the Note
	 * @param token :- Get the Token in User
	 * @param id :- Which Position Delete
	 * @return :- Response
	 */
	@DeleteMapping("/deleteNote/{id}")
	public ResponseEntity<String> deleteNote(@RequestHeader String token,@PathVariable  int id){
		Response response = service.deleteNote(token,id);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Getting the Note
	 * @param token :- Get the Token in User
	 * @return :- Response
	 */
	@GetMapping("/getNotes")
	public ResponseEntity<Response> showAllNote(@RequestHeader String token) 
	{
		Response response = service.getNotes(token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Sorted By Title 
	 * @param order :- Which Order(Ascending or Descending Order)
	 * @param token :- Verified the Token
	 * @return :- Response
	 */
	@GetMapping("/sortByTitle/{order}")
	public ResponseEntity<Response> sortByTitle(@RequestHeader String token,@PathVariable String order){
		Response response = service.sortByTitle(token,order);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	/**
	 * Purpose :-Sorted by Discription 
	 * @param token :- Verified the Token
	 * @param order :- Which Order(Ascending or Descending Order)
	 * @return :- Response
	 */
	@GetMapping("/sortByDescription/{order}")
	public ResponseEntity<Response>sortByDescription(@RequestHeader String token,@PathVariable String order){
		Response response = service.sortByDescription(token, order);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Sorted By Date
	 * @param token :- Verified the Token
	 * @param order :- Which Order(Ascending or Descending Order)
	 * @return :- Response
	 */
	@GetMapping("/sortByDate/{order}")
	public ResponseEntity<Response> sortByDate(@RequestHeader String token,@PathVariable String order){
		Response response = service.sortByDate(token,order);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
