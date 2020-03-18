package com.bridgelabz.fundoonotesapi.controller;

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

import com.bridgelabz.fundoonotesapi.dto.CreateLabelDto;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.service.LabelService;
/**
 * @author :- Krunal Parate
 * Purpose :- All API Created
 */
@RestController
@RequestMapping("/lableapi")
public class LabelController {
	@Autowired
	private LabelService service;

	/**
	 * Purpose :- Created Labels
	 * @param token :- Get the Token in User
	 * @param createLabelDto 
	 * @return :- Response
	 */
	@PostMapping("/createLable")
	public ResponseEntity<String> createLabel(@RequestHeader String token,@RequestBody CreateLabelDto  createLabelDto){
		Response response = service.createLabel(token,createLabelDto);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Updated Labels
	 * @param token :- Get the Token in User
	 * @param createLabelDto
	 * @param id :-Which position Update
	 * @return :- Response
	 */
	@PutMapping("/updateLable/{labelId}")
	public ResponseEntity<String>updateLabel(@RequestHeader String token,@RequestBody CreateLabelDto  createLabelDto,@PathVariable int labelId){
		Response response = service.updateNote(token,createLabelDto, labelId);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Deleted Labels
	 * @param token :- Get the Token in User
	 * @param id :- Which position Delete label in Labels entity
	 * @return :- Response
	 */
	@DeleteMapping("/deleteLable/{labelId}")
	public ResponseEntity<String> deleteLabel(@RequestHeader String token,@PathVariable int labelId){
		Response response = service.deleteNote(token, labelId);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Getting All Details
	 * @param token :-Get the Token in User
	 * @return :- Response
	 */
	@GetMapping("/getLabels")
	public ResponseEntity<Response> getLabels(String token) 
	{
		Response response= service.getLabels(token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * @param token :- Verified the Token
	 * @param noteId :- Which position add the note in NotesLabels entity
	 * @param labelId :-Which position add the labels in NotesLabels entity
	 * @return :- Response
	 */
	@PostMapping("/AddLablesInNotes/{noteId}/{labelId}")
	public ResponseEntity<Response> AddLablesInNotes(String token, int noteId,int labelId) 
	{
		Response response= service.AddLablesInNotes(token, noteId, labelId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
