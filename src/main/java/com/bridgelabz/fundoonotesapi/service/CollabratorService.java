package com.bridgelabz.fundoonotesapi.service;

import com.bridgelabz.fundoonotesapi.dto.CollabratorDto;
import com.bridgelabz.fundoonotesapi.responce.Response;
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
