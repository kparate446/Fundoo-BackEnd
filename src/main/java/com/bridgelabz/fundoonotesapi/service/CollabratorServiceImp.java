package com.bridgelabz.fundoonotesapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.dto.CollabratorDto;
import com.bridgelabz.fundoonotesapi.exception.InvalidCollabratorException;
import com.bridgelabz.fundoonotesapi.exception.InvalidNoteException;
import com.bridgelabz.fundoonotesapi.exception.InvalidUserException;
import com.bridgelabz.fundoonotesapi.exception.ReceiverMailAlreadyPresentException;
import com.bridgelabz.fundoonotesapi.message.MessageData;
import com.bridgelabz.fundoonotesapi.model.Collabrator;
import com.bridgelabz.fundoonotesapi.model.Notes;
import com.bridgelabz.fundoonotesapi.model.User;
import com.bridgelabz.fundoonotesapi.repository.CollabratorRepository;
import com.bridgelabz.fundoonotesapi.repository.NotesRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;
import com.sun.istack.logging.Logger;
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
	private static final Logger LOGGER = Logger.getLogger(CollabratorServiceImp.class);

	/** Collabrator Created */
	public Response createCollabrator(String token, int id, CollabratorDto collabratorDto) {
		Collabrator collabrator = mapper.map(collabratorDto, Collabrator.class);
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		
		String email = jwtToken.getToken(token);
		User user1 = userRepository.findByEmail(email);
		if (userRepository.findByEmail(email) == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (notes == null) {
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
		// Check the User Sender Mail and Receiver Mail are same. 
		for (Collabrator collabrator1 : notes.getCollabrators()) {
			if (collabrator1.getMailSender().equals(collabratorDto.getMailReceiver())){ /// ***
				LOGGER.warning("User are already present");
				throw new ReceiverMailAlreadyPresentException(messageData.ReceiverMail_Already_Present);
			}
		}
		//check if it belong to user note
		if(notes.getUser().getId() == user.getId()) {
			for(User user : userRepository.findAll()) {
				if(user.getEmail().equals(collabratorDto.getMailReceiver())) {
					
		
		// Check the Collabrator are Already Present. 
		for (Collabrator collabrator1 : notes.getCollabrators()) {
			if (collabrator1.getMailReceiver().equals(collabratorDto.getMailReceiver())){ /// ***
				LOGGER.warning("User are already present");
				throw new ReceiverMailAlreadyPresentException(messageData.ReceiverMail_Already_Present);
			}
		}
		if (notes.getUser().getId() == userRepository.findByEmail(email).getId()) {
			collabrator.setMailReceiver(collabratorDto.getMailReceiver());
			collabrator.setMailSender(user1.getEmail());
			collabrator.setNotes(notes);
			collabratorRepository.save(collabrator);
			LOGGER.info("Collabrator added Succesfully in Collabrator table");
			return new Response(200, "Collabrator Created Successfully", true);
		} else {
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
				}
			}
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Collabrator Deleted */
	public Response deletedCollabrator(String token, int id) {
		String email = jwtToken.getToken(token);
		Collabrator collabrator = collabratorRepository.findById(id).orElseThrow(() -> new InvalidCollabratorException(messageData.Invalid_Collabrator));
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (collabratorRepository.findById(id) != null) {
			collabratorRepository.deleteById(collabrator.getId());
			LOGGER.info("Successfully Collabrator Deleted");
			return new Response(200, "Collabrator Deleted Successfully", true);
		} else {
			LOGGER.warning("Invalid Collabrator");
			throw new InvalidCollabratorException(messageData.Invalid_Collabrator);
		}
	}

	/** Collabrator updated */
	public Response updatedCollabrator(String token, int id, CollabratorDto collabratorDto) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (collabratorRepository.findById(id)==null) {
			LOGGER.warning("Invalid Collabrator");
			throw new InvalidCollabratorException(messageData.Invalid_Collabrator);
		}else {
			Collabrator collabrator = collabratorRepository.findById(id).orElseThrow(() -> new InvalidCollabratorException(messageData.Invalid_Collabrator));
			collabrator.setMailReceiver(collabratorDto.getMailReceiver());
			collabratorRepository.save(collabrator);
			LOGGER.info("Successfully collabrator updated");
			return new Response(200, "Collabrator Updated Successfully", true);
		}
	}
	/** Show Collabrator*/
	public Response getCollabrator(String token) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if(user.getNotes()!=null) {
			List<Collabrator>collabrator = collabratorRepository.findAll().stream().filter(e ->e.getNotes().getUser().getId()==user.getId()).collect(Collectors.toList());
			LOGGER.info("Successfully showing the Collabrator table data");
			return	new Response(200, "Show the All Collabrator Successfully ", collabrator);
		}
		LOGGER.warning("Invalid Collabrator");
		throw new InvalidCollabratorException(messageData.Invalid_Collabrator);
	}
}