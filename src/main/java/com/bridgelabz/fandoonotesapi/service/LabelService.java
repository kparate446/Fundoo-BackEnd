package com.bridgelabz.fandoonotesapi.service;

import com.bridgelabz.fandoonotesapi.dto.CreateLabelDto;
import com.bridgelabz.fandoonotesapi.responce.Response;
/**
 * @author admin1
 * Purpose :- Creating the LabelService Interface
 */
public interface LabelService {
	Response createLabel(String token, CreateLabelDto createLabelDto);
	Response updateNote(String token, CreateLabelDto createLabelDto, int id);
	Response deleteNote(String token, int id);
	Response getLabels(String token);
}
