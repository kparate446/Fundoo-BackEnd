package com.bridgelabz.fandoonotesapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.bridgelabz.fandoonotesapi.dto.CollabratorDto;
import com.bridgelabz.fandoonotesapi.model.Collabrator;
import com.bridgelabz.fandoonotesapi.model.Notes;
import com.bridgelabz.fandoonotesapi.model.User;
import com.bridgelabz.fandoonotesapi.repository.CollabratorRepository;
import com.bridgelabz.fandoonotesapi.repository.NotesRepository;
import com.bridgelabz.fandoonotesapi.repository.UserRepository;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.utility.JwtToken;

@Service
public class CollabratorServiceImp implements CollabratorService {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private CollabratorRepository collaboratorRepository;
	@Autowired
	private UserRepository userRepository;

	private User user;
	// SimpleMailMessage--> InBuild Class
	SimpleMailMessage email;
	@Autowired
	private JwtToken jwtToken;
	String message;

	/** Collabrator Created */
	public Response createCollabrator(String token, int id, CollabratorDto collabratorDto) {
		Collabrator collabrator = mapper.map(collabratorDto, Collabrator.class);
		Notes notes = notesRepository.findById(id);
		System.out.println("Notes CLASS " + collabrator);
		String email = jwtToken.getToken(token);
		System.out.println("SenderMail" + email);
		User user1 = userRepository.findByEmail(email);
		System.out.println("USER" + user1);
		System.out.println(token);
		if (userRepository.findByEmail(email) == null) {
			System.out.println("User Not Exit");
			return new Response(400, "Invalid Account", token);
		}
		if (notes == null) {
			return new Response(400, "Note Not Present", token);
		}

		for (Collabrator collabrator1 : notes.getCollabrators()) {
			if (collabrator1.getMailReceiver().equals(collabratorDto.getMailReceiver())) { /// ***
				return new Response(400, "User Are Already Present", token);
			}
		}
		if (notes.getUser().getId() == userRepository.findByEmail(email).getId()) {
			collabrator.setMailReceiver(collabratorDto.getMailReceiver());
			collabrator.setMailSender(user1.getEmail());
			collabrator.setNotes(notes);
			collaboratorRepository.save(collabrator);
			return new Response(200, "Collabrator Created Successfully", token);
		} else {
			return new Response(400, "Note does Not Belongs to user", token);
		}
	}

	/** Collabrator Deleted */
	public Response deletedCollabrator(String token, int id) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("User Not Exit");
			return new Response(400, "Invalid Account", token);
		}
		if (collaboratorRepository.findById(id) != null) {
			collaboratorRepository.deleteById(collaboratorRepository.findById(id).getId());
			return new Response(200, "Collabrator Deleted Successfully", token);
		} else {
			return new Response(400, "Collabrator does Not Belongs to user", token);
		}
	}
	/** Collabrator updated*/
	public Response updatedCollabrator(String token) {
		return null;
		
	}
}
