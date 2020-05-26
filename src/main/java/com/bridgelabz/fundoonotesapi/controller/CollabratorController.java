package com.bridgelabz.fundoonotesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesapi.dto.CollabratorDto;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.service.CollabratorService;

/**
 * @author :- Krunal Parate
 * Purpose :-  Created Add, Update, Delete Label API
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController 
@RequestMapping("/collabratorapi")
public class CollabratorController {
	@Autowired // Declaired the Dependency
	private CollabratorService service;
	/**
	 * Purpose :- Creating Collabrator
	 * @param token :- Verified the Token
	 * @param id :- Which Note id Collabrator in Collabrator Entity
	 * @param collabratorDto :- receive the mail Id
	 * @return :- Response
	 */
	// Add Note id
	@PostMapping("/createCollabrator/{noteId}")
	public ResponseEntity<String> createCollabrator(@RequestHeader String token,@PathVariable int noteId,@RequestBody CollabratorDto collabratorDto){
		Response response = service.createCollabrator(token, noteId, collabratorDto);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * @param token :-Verified the Token
	 * @param id :- Which position Collabrator id delete in Labels entity
	 * @return :- Response
	 */
	@DeleteMapping("/deleteCollabrator/{collabratorId}")
	public ResponseEntity<String> deleteCollabrate(@RequestHeader String token,@PathVariable int collabratorId){
		Response response = service.deletedCollabrator(token, collabratorId);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Updated Collabrator
	 * @param token :-Verified the Token
	 * @param id :-Which Collabrator id updated in Collabrator Entity
	 * @param collabratorDto
	 * @return :- Response
	 */
	@PutMapping("/updatedCollabrator/{collabratorId}")
	public ResponseEntity<String> updateCollabrator(@RequestHeader String token,@PathVariable int collabratorId,@RequestBody CollabratorDto collabratorDto){
		Response response = service.updatedCollabrator(token, collabratorId, collabratorDto);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Show Collabrator Data
	 * @param token :- Verified the Token
	 * @return :- Response
	 */
	@GetMapping("/getCollabrator")
	public ResponseEntity<Response> showCollabrator(@RequestHeader String token){
		Response response = service.getCollabrator(token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
