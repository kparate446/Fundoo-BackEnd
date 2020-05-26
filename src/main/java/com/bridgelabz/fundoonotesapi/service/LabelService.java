package com.bridgelabz.fundoonotesapi.service;

import com.bridgelabz.fundoonotesapi.dto.CreateLabelDto;
import com.bridgelabz.fundoonotesapi.responce.Response;
/**
 * @author admin1
 * Purpose :- Creating the LabelService Interface
 */
public interface LabelService {
	Response createLabel(String token, CreateLabelDto createLabelDto);
	Response updateNote(String token, CreateLabelDto createLabelDto, int id);
	Response deleteNote(String token, int id);
	Response getLabels(String token);
	Response AddLablesInNotes(String token, int noteId,int labelId);
	Response DeleteLablesInNotes(String token, int noteId,int labelId);
}
