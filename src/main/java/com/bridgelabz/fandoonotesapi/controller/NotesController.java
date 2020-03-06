package com.bridgelabz.fandoonotesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.bridgelabz.fandoonotesapi.service.Services;

@RestController 
@RequestMapping("/notesapi")
public class NotesController {
	@Autowired // Declaired the Dependency
	private Services service;
	
	@PostMapping("/createNote")
	public ResponseEntity<String> createNote(@RequestHeader String token,@RequestBody CreateNoteDto createNoteDto){
		Response response = service.createNote(token,createNoteDto);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	@PutMapping("/updateNote/{id}")
	public ResponseEntity<String> UpdateNote(@RequestHeader String token,@RequestBody UpdateNoteDto updateNoteDto,@PathVariable  int id){
		Response response = service.updateNote(token,updateNoteDto,id);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	@DeleteMapping("/deleteNote/{id}")
	public ResponseEntity<String> deleteNote(@RequestHeader String token,@PathVariable  int id){
		Response response = service.deleteNote(token,id);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
}
