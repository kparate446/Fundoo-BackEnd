package com.bridgelabz.fundoonotesapi.service;

import com.bridgelabz.fundoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fundoonotesapi.dto.ReminderDto;
import com.bridgelabz.fundoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fundoonotesapi.responce.Response;
/**
 * @author :- Krunal Parate
 * Purpose :- Creating NoteService interface
 */
public interface NoteService {
	Response createNote(String token, CreateNoteDto createNoteDto)  throws Exception ;
	Response updateNote(String token, UpdateNoteDto updateNoteDto, int id);
	Response deleteNote(String token, int id);
	Response getNotes(String token);
	Response sortByTitle(String token,String order);
	Response sortByDescription(String token,String order);
	Response sortByDate(String token,String order);
	Response pinNotes(String token,int id);
	Response trashedNotes(String token,int id);
	Response archiveNotes(String token,int id);
	Response findById(String token,int id);
	Response createReminder(String token, ReminderDto reminderDto, int id);
	Response deleteReminder(String token,int id);
	Response updateReminder(String token, ReminderDto reminderDto, int id);
	Response getReminder(String token);
	Response findByTitle(String token, String Title);
	Response findByDiscription(String token, String discription);
	
	Response findByIdElasticSearch(String id) throws Exception;
	Response findByDiscriptionElasticSearch(String discription) throws Exception;
}