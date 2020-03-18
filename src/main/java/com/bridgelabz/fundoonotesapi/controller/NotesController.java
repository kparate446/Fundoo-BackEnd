package com.bridgelabz.fundoonotesapi.controller;

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

import com.bridgelabz.fundoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fundoonotesapi.dto.ReminderDto;
import com.bridgelabz.fundoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.service.NoteService;
/**
 * @author :- Krunal Parate
 * Purpose :- Creating the API
 */
@RestController 
@RequestMapping("/notesapi")
public class NotesController {
	@Autowired // Declared the Dependency
	private NoteService service;
	/**
	 * Purpose :- Created the note in Notes entity
	 * @param token :- Verified the Token
	 * @param createNoteDto 
	 * @return :- Response
	 */
	@PostMapping("/createNote")
	public ResponseEntity<String> createNote(@RequestHeader String token,@Valid @RequestBody CreateNoteDto createNoteDto) throws Exception{
		Response response = service.createNote(token,createNoteDto);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Updated the note in Notes entity
	 * @param token:- Verified the Token
	 * @param updateNoteDto
	 * @param id:- Which position Update note in Notes entity
	 * @return :-Response
	 * @throws Exception :- Handle the Exception
	 */
	@PutMapping("/updateNote/{noteId}")
	public ResponseEntity<String> UpdateNote(@RequestHeader String token,@Valid @RequestBody UpdateNoteDto updateNoteDto,@PathVariable  int noteId) throws Exception{
		Response response = service.updateNote(token,updateNoteDto,noteId);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Deleted the Note
	 * @param token :- Verified the Token
	 * @param id :- Which position Delete note in Notes entity
	 * @return :- Response
	 */
	@DeleteMapping("/deleteNote/{noteId}")
	public ResponseEntity<String> deleteNote(@RequestHeader String token,@PathVariable  int noteId){
		Response response = service.deleteNote(token,noteId);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Getting the Note
	 * @param token :- Verified the Token
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
	/**
	 * Purpose :- Pin & Unpin Notes
	 * @param token :- Verified the Token
	 * @param id :- Which Position Pin/Unpin
	 * @return :- Response
	 */
	@PostMapping("/pinNotes/{noteId}")
	public ResponseEntity<Response> pinNotes(@RequestHeader String token ,@PathVariable int noteId){
		Response response = service.pinNotes(token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Trashed & Restore Notes
	 * @param token :-Verified the Token
	 * @param id :- Which Position Deleted/Restore
	 * @return :- Response
	 */
	@PostMapping("/trashedNotes/{noteId}")
	public ResponseEntity<Response> trashedNotes(@RequestHeader String token ,@PathVariable int noteId){
		Response response = service.trashedNotes(token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Archive & Unarchieve Notes
	 * @param token :- Verified the Token
	 * @param id :- Which Position Archive/Unarchive in Notes entity
	 * @return :- Response
	 */
	@PostMapping("/archiveNotes/{noteId}")
	public ResponseEntity<Response> archiveNotes(@RequestHeader String token ,@PathVariable int noteId){
		Response response = service.archiveNotes(token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Searching the notes Based on the Id
	 * @param token :- Verified the Token
	 * @param id :- Which Position Id Access the Data in Notes entity
	 * @return :- Response
	 */
	@GetMapping("/findById/{noteId}")
	public ResponseEntity<Response> findById(@RequestHeader String token ,@PathVariable int noteId){
		Response response = service.findById(token, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Add Reminder
	 * @param token :- Verified the Token
	 * @param reminderDto :- Getting the Date and time in Reminder Entity
	 * @param id :- Which Note add the Remider
	 * @return :- Response
	 */
	@PostMapping("/addReminder/{noteId}")
	public ResponseEntity<Response> addReminder(@RequestHeader String token ,@RequestBody ReminderDto reminderDto,@PathVariable int noteId){
		Response response = service.createReminder(token, reminderDto, noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Delete Reminder
	 * @param token :- Verified the token
	 * @param id :- Which position reminder Deleted in Reminder entity
	 * @return :- Response
	 */
	@DeleteMapping("/deleteReminder/{reminderId}")
	public ResponseEntity<Response> deleteReminder(@RequestHeader String token ,@PathVariable int reminderId){
		Response response = service.deleteReminder(token, reminderId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Updated Reminder
	 * @param token :- Verified the token
	 * @param reminderDto :- Update the Date and time in Reminder entity
	 * @param id :- Which remider are updated
	 * @return :- Response
	 */
	@PutMapping("/updateReminder/{reminderId}")
	public ResponseEntity<Response> updateReminder(@RequestHeader String token ,@RequestBody ReminderDto reminderDto,@PathVariable int reminderId) throws Exception{
		Response response = service.updateReminder(token, reminderDto, reminderId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Show all Reminder
	 * @param token :- Verified the Token 
	 * @return :- Response
	 */
	@GetMapping("/getReminder")
	public ResponseEntity<Response> showAllReminder(@RequestHeader String token) 
	{
		Response response = service.getReminder(token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Searching the notes Based on the Title
	 * @param token :-Verified the Token 
	 * @param title :- Which Title access note of Data in Notes Entity
	 * @return :- Response
	 */
	@GetMapping("/findByTitle/{title}")
	public ResponseEntity<Response> findByTitle(@RequestHeader String token ,@PathVariable String title){
		Response response = service.findByTitle(token, title);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Searching the notes Based on the Description
	 * @param token :- Verified the Token
	 * @param discription :- Which Description access note of Data in Notes Entity
	 * @return :- Response
	 */
	@GetMapping("/findByDescription/{description}")
	public ResponseEntity<Response> findByDescription(@RequestHeader String token ,@PathVariable String description){
		Response response = service.findByDescription(token, description);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**************Elastic Search ***************************/
	/**
	 * Purpose :- Searching the notes based on the Id in Elastic Search
	 * @param token :- Verified the Token
	 * @param noteId :- Which Position Id Access the Data
	 * @return :- Response
	 * @throws Exception :- Handle the Exception
	 */
	@GetMapping("/findByIdElasticSearch/{noteId}")
	public ResponseEntity<Response> findByIdElasticSearch(@RequestHeader String token ,@PathVariable String noteId) throws Exception{
		Response response = service.findByIdInElasticSearch(token,noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	/**
	 * Purpose :- Deleted note by Elastic Search
	 * @param token :- Verified the Token
	 * @param noteId :-Which positon of notes is deleted
	 * @return :- Response 
	 * @throws Exception :- Handle the Exception
	 */
	@GetMapping("/deleteNoteByElasticSearch/{noteId}")
	public ResponseEntity<Response> deleteNoteByElasticSearch(@RequestHeader String token ,@PathVariable String noteId) throws Exception{
		Response response = service.deleteNoteByElasticSearch(token,noteId);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	/** 
	 * Purpose :- Searching the notes based on Title in Elastic Search
	 * @param token :- Verified the Token
	 * @param title :- Which Title access note of Data
	 * @return :- Response
	 * @throws Exception :-Handle the Exception
	 */
	@GetMapping("/findByTitleInElasticSearch/{title}")
	public ResponseEntity<Response> findByTitleInElasticSearch(@RequestHeader String token , @PathVariable String title) throws Exception{
		Response response = service.findByTitleInElasticSearch(token,title);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
