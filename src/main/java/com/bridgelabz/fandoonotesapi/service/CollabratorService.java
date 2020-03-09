package com.bridgelabz.fandoonotesapi.service;

import com.bridgelabz.fandoonotesapi.dto.CollabratorDto;
import com.bridgelabz.fandoonotesapi.responce.Response;

public interface CollabratorService {
	Response createCollabrator(String token,int id, CollabratorDto collabratorDto);
	Response deletedCollabrator(String token, int id);
}
