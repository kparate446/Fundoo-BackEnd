package com.bridgelabz.fandoonotesapi.service;

import com.bridgelabz.fandoonotesapi.dto.CollabratorDto;
import com.bridgelabz.fandoonotesapi.responce.Response;
/**
 * @author admin1
 * Purpose :- Creating the CollabratorService Interface
 */
public interface CollabratorService {
	Response createCollabrator(String token,int id, CollabratorDto collabratorDto);
	Response deletedCollabrator(String token, int id);
	Response updatedCollabrator(String token, int id, CollabratorDto collabratorDto);
	Response getCollabrator(String token);
}
