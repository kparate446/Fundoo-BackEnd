package com.bridgelabz.fandoonotesapi.service;

import com.bridgelabz.fandoonotesapi.dto.CreateNoteDto;
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
//	List<Notes> showAllNotes(int id,String token);
	Response getNotes(String token);
	Response sortByTitle(String token,String order);
	Response sortByDescription(String token,String order);
	Response sortByDate(String token,String order);
}