package com.bridgelabz.fandoonotesapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.bridgelabz.fandoonotesapi.dto.CollabratorDto;
import com.bridgelabz.fandoonotesapi.exception.InvalidCollabratorException;
import com.bridgelabz.fandoonotesapi.exception.InvalidNoteException;
import com.bridgelabz.fandoonotesapi.exception.InvalidUserException;
import com.bridgelabz.fandoonotesapi.exception.ReceiverMailAlreadyPresentException;
import com.bridgelabz.fandoonotesapi.message.MessageData;
import com.bridgelabz.fandoonotesapi.model.Collabrator;
import com.bridgelabz.fandoonotesapi.model.Notes;
import com.bridgelabz.fandoonotesapi.model.User;
import com.bridgelabz.fandoonotesapi.repository.CollabratorRepository;
import com.bridgelabz.fandoonotesapi.repository.NotesRepository;
import com.bridgelabz.fandoonotesapi.repository.UserRepository;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.utility.JwtToken;
/**
 * @author :- Krunal Parate
 * Purpose :-  Implementing the Create,Update,Delete Collabrator
 */
@Service
public class CollabratorServiceImp implements CollabratorService {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private CollabratorRepository collabratorRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageData messageData;

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
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (notes == null) {
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
		// Check the User Sender Mail and Receiver Mail are same. 
		for (Collabrator collabrator1 : notes.getCollabrators()) {
			if (collabrator1.getMailSender().equals(collabratorDto.getMailReceiver())){ /// ***
				throw new ReceiverMailAlreadyPresentException(messageData.ReceiverMail_Already_Present);
			}
		}
		// Check the Collabrator are Already Present. 
		for (Collabrator collabrator1 : notes.getCollabrators()) {
			if (collabrator1.getMailReceiver().equals(collabratorDto.getMailReceiver())){ /// ***
				throw new ReceiverMailAlreadyPresentException(messageData.ReceiverMail_Already_Present);
			}
		}
		//if(!notes.getUser().getEmail().equals(userRepository.findByEmail(email)))
		if (notes.getUser().getId() == userRepository.findByEmail(email).getId()) {
			collabrator.setMailReceiver(collabratorDto.getMailReceiver());
			collabrator.setMailSender(user1.getEmail());
			collabrator.setNotes(notes);
			collabratorRepository.save(collabrator);
			return new Response(200, "Collabrator Created Successfully", token);
		} else {
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
	}

	/** Collabrator Deleted */
	public Response deletedCollabrator(String token, int id) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (collabratorRepository.findById(id) != null) {
			collabratorRepository.deleteById(collabratorRepository.findById(id).getId());
			return new Response(200, "Collabrator Deleted Successfully", token);
		} else {
			throw new InvalidCollabratorException(messageData.Invalid_Collabrator);
		}
	}

	/** Collabrator updated */
	public Response updatedCollabrator(String token, int id, CollabratorDto collabratorDto) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (collabratorRepository.findById(id)==null) {
			throw new InvalidCollabratorException(messageData.Invalid_Collabrator);
		}else {
			Collabrator collabrator	=collabratorRepository.findById(id);
			collabrator.setMailReceiver(collabratorDto.getMailReceiver());
			collabratorRepository.save(collabrator);
			return new Response(200, "Collabrator Updated Successfully", token);
		}
	}
	/** Show Collabrator*/
	public Response getCollabrator(String token) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if(user.getNotes()!=null) {
			List<Collabrator>collabrator = collabratorRepository.findAll().stream().filter(e ->e.getNotes().getUser().getId()==user.getId()).collect(Collectors.toList());
			return	new Response(200, "Show the All Collabrator Successfully ", collabrator);
		}
		throw new InvalidCollabratorException(messageData.Invalid_Collabrator);
	}
}
