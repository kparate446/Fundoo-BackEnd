package com.bridgelabz.fandoonotesapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fandoonotesapi.dto.CreateLabelDto;
import com.bridgelabz.fandoonotesapi.exception.InvalidLabelException;
import com.bridgelabz.fandoonotesapi.exception.InvalidNoteException;
import com.bridgelabz.fandoonotesapi.exception.InvalidUserException;
import com.bridgelabz.fandoonotesapi.message.MessageData;
import com.bridgelabz.fandoonotesapi.model.Labels;
import com.bridgelabz.fandoonotesapi.model.User;
import com.bridgelabz.fandoonotesapi.repository.LabelsRepository;
import com.bridgelabz.fandoonotesapi.repository.UserRepository;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.utility.JwtToken;
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
	private MessageData messageData;
	@Autowired
	private JwtToken jwtToken;
	private User user;
	String message;

	/** Create Labels*/
	public Response createLabel(String token, CreateLabelDto createLabelDto) {
		Labels labels= mapper.map(createLabelDto, Labels.class);
		System.out.println("Labels CLASS " + labels);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (labels != null) {
			labels.setLabelName(createLabelDto.getLabelName());
			labels.setUser(user);// Add the User Id
			labelRepository.save(labels);
			return new Response(200, "Created Labels", token);
		} else {
			System.out.println("Note Not Present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
	}
	/** Updated Labels */
	public Response updateNote(String token, CreateLabelDto createLabelDto, int id) {
		Labels labels = labelRepository.findById(id);
		System.out.println("Notes CLASS " + labels);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (labels != null) {
			labels.setLabelName(createLabelDto.getLabelName());
			labelRepository.save(labels);
			return new Response(200, "Updated Labels", token);
		} else {
			System.out.println("Labels Not Present");
			throw new InvalidLabelException(messageData.Invalid_Label);
		}
	}
	/** Deleted Labels */
	public Response deleteNote(String token, int id) {
		Labels labels = labelRepository.findById(id);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		} 
		//		else if(user.getNotes()!=null) 
		if(labels.getUser().getId()== user.getId()) {
			labelRepository.deleteById(id);
			return new Response(200, "Deleted Label Successfully", token);
		} else {
			System.out.println("Note Not Present");
			throw new InvalidLabelException(messageData.Invalid_Label);
		}
	}
	/** Getting All Labels*/
	public Response getLabels(String token){
		//		Notes notes = (Notes) notesRepository.findAll();
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if(user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		} if(user.getNotes()!=null) {
			List<Labels>labels = labelRepository.findAll().stream().filter(e ->e.getUser().getId()==user.getId()).collect(Collectors.toList());
			return	new Response(200, "Show All Labels ", labels);
		}
		throw new InvalidLabelException(messageData.Invalid_Label);
	}
}
