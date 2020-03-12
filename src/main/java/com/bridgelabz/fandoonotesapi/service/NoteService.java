package com.bridgelabz.fandoonotesapi.service;

import com.bridgelabz.fandoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fandoonotesapi.dto.ReminderDto;
import com.bridgelabz.fandoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fandoonotesapi.responce.Response;
/**
 * @author :- Krunal Parate
 * Purpose :- Creating NoteService interface
 */
public interface NoteService {
	Response createNote(String token, CreateNoteDto createNoteDto);
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
}