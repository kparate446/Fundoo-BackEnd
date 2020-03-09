package com.bridgelabz.fandoonotesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fandoonotesapi.dto.CollabratorDto;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.service.CollabratorService;

@RestController 
@RequestMapping("/collabratorapi")
public class CollabratorController {
	@Autowired // Declaired the Dependency
	private CollabratorService service;
	
	/**
	 * Purpose :- Creating Collabrator
	 * @param token :- Verified the Token
	 * @param id :- Which Note id Collabrator
	 * @param collabratorDto :- receive the mail Id
	 * @return :- Response
	 */
	@PostMapping("/createCollabrator/{id}")
	public ResponseEntity<String> createCollabrator(@RequestHeader String token,@PathVariable int id,@RequestBody CollabratorDto collabratorDto){
		Response response = service.createCollabrator(token, id, collabratorDto);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * @param token :-Verified the Token
	 * @param id :- Which Collabrator id deleted
	 * @return :- Response
	 */
	@DeleteMapping("/deleteCollabrator/{id}")
	public ResponseEntity<String> deleteCOllabrate(@RequestHeader String token,@PathVariable int id){
		Response response = service.deletedCollabrator(token, id);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
}
