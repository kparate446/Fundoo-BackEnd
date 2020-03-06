package com.bridgelabz.fandoonotesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fandoonotesapi.dto.CreateLabelDto;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.service.LabelService;
/**
 * @author :- Krunal Parate
 * Purpose :- Created Add, Update, Delete Label API
 */
@RestController
@RequestMapping("/lableapi")
public class LabelController {
	@Autowired
	private LabelService service;

	/**
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
	 * @param token :- Get the Token in User
	 * @param createLabelDto
	 * @param id :-Which position Update
	 * @return :- Response
	 */
	@PostMapping("/updateLable/{id}")
	public ResponseEntity<String>updateLabel(@RequestHeader String token,@RequestBody CreateLabelDto  createLabelDto,@PathVariable int id){
		Response response = service.updateNote(token,createLabelDto, id);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * @param token :- Get the Token in User
	 * @param id :- Which Position Deleted
	 * @return :- Response
	 */
	@PutMapping("/deleteLable/{id}")
	public ResponseEntity<String> deleteLabel(@RequestHeader String token,@PathVariable int id){
		Response response = service.deleteNote(token, id);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
}
