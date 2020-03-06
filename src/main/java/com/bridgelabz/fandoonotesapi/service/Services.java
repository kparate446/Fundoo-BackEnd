package com.bridgelabz.fandoonotesapi.service;

import com.bridgelabz.fandoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fandoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fandoonotesapi.responce.Response;


public interface Services {
	Response createNote(String token, CreateNoteDto createNoteDto);
	Response updateNote(String token, UpdateNoteDto updateNoteDto, int id);
	Response deleteNote(String token, int id);
}