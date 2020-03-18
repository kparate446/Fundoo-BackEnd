package com.bridgelabz.fundoonotesapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotesapi.dto.CreateLabelDto;
import com.bridgelabz.fundoonotesapi.exception.InvalidLabelException;
import com.bridgelabz.fundoonotesapi.exception.InvalidNoteException;
import com.bridgelabz.fundoonotesapi.exception.InvalidUserException;
import com.bridgelabz.fundoonotesapi.exception.LabelAlreadyPresentException;
import com.bridgelabz.fundoonotesapi.message.MessageData;
import com.bridgelabz.fundoonotesapi.model.Labels;
import com.bridgelabz.fundoonotesapi.model.Notes;
import com.bridgelabz.fundoonotesapi.model.User;
import com.bridgelabz.fundoonotesapi.repository.LabelsRepository;
import com.bridgelabz.fundoonotesapi.repository.NotesRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;
import com.sun.istack.logging.Logger;
/**
 * @author :- Krunal Parate
 * Purpose :-  Implementing the Create,Update,Delete Notes
 */
@Service
public class LabelServiceImp implements LabelService{
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private LabelsRepository labelRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private MessageData messageData;
	@Autowired
	private JwtToken jwtToken;
	private User user;
	String message;
	private static final Logger LOGGER = Logger.getLogger(LabelServiceImp.class);
	/** Create Labels*/
	public Response createLabel(String token, CreateLabelDto createLabelDto) {
		Labels labels= mapper.map(createLabelDto, Labels.class);
		System.out.println("Labels CLASS " + labels);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		// Label is Already Exit
		for(Labels labels1 : user.getLabels())
			if(labels1.getLabelName().equals(createLabelDto.getLabelName())) {
				LOGGER.warning("Label Already Present");
				return new Response(400, "Label Already Present", false);
			}
		if (labels != null ){
			labels.setLabelName(createLabelDto.getLabelName());
			labels.setUser(user);// Add the User Id
			labelRepository.save(labels);
			LOGGER.info("Created label Succesfully in Label table");
			return new Response(200, "Created Labels", true);
		} else {
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
	}
	/** Updated Labels */
	public Response updateNote(String token, CreateLabelDto createLabelDto, int id) {
		Labels labels = labelRepository.findById(id).orElseThrow(() -> new InvalidLabelException(messageData.Invalid_Label));
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (labels != null) {
			labels.setLabelName(createLabelDto.getLabelName());
			labelRepository.save(labels);
			LOGGER.info("Successfully Updated Labels in labels tabel");
			return new Response(200, "Updated Labels", true);
		} else {
			LOGGER.warning("Label Not Present");
			throw new InvalidLabelException(messageData.Invalid_Label);
		}
	}
	/** Deleted Labels */
	public Response deleteNote(String token, int id) {
		Labels labels = labelRepository.findById(id).orElseThrow(() -> new InvalidLabelException(messageData.Invalid_Label));
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} 
		if(labels.getUser().getId()== user.getId()) {
			labelRepository.deleteById(id);
			LOGGER.info("Successfully Deleted Labels in labels tabel");
			return new Response(200, "Deleted Label Successfully", true);
		} else {
			LOGGER.warning("Label Not Present");
			throw new InvalidLabelException(messageData.Invalid_Label);
		}
	}
	/** Getting All Labels*/
	public Response getLabels(String token){
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if(user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} if(user.getNotes()!=null) {
			List<Labels>labels = labelRepository.findAll().stream().filter(e ->e.getUser().getId()==user.getId()).collect(Collectors.toList());
			LOGGER.info("Successfully showing the Labels table data");
			return	new Response(200, "Show All Labels ", labels);
		}
		LOGGER.warning("Label Not Present");
		throw new InvalidLabelException(messageData.Invalid_Label);
	}

	/** Adding Labels In Notes */
	public Response AddLablesInNotes(String token, int noteId,int labelId) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} 
		Notes note = notesRepository.findById(noteId).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		if(note.getUser().getId()!= user.getId()) {
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
		for(Labels labels : user.getLabels()) {
			if(labels.getId() == labelId) {
				List<Labels> listOfLabels = note.getListOfLabels();
				List<Notes> listOfNotes = labels.getListOfNotes();
				for(Labels labels1 : listOfLabels) {
					if(labels1.getId() == labelId) {
						LOGGER.warning("Label Alredy Present");
						throw new LabelAlreadyPresentException(messageData.LabelAlready_Present);
					}
				}
				listOfLabels.add(labels);
				listOfNotes.add(note);
				note.setListOfLabels(listOfLabels);
				labels.setListOfNotes(listOfNotes);
				notesRepository.save(note);
				labelRepository.save(labels);
				LOGGER.info("Successfully Added Labels in Notes");
				return new Response(200, "Successfully added Labels in Notes", true);
			}
		}
		LOGGER.warning("Label Not Present");
		throw new InvalidLabelException(messageData.Invalid_Label);
	}
}

