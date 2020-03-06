package com.bridgelabz.fandoonotesapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fandoonotesapi.dto.CreateLabelDto;
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
			return new Response(400, "Invalid Account", token);
		} else if (labels != null) {
			labels.setLabelName(createLabelDto.getLabelName());
			labelRepository.save(labels);
			return new Response(200, "Created Labels", token);
		} else {
			System.out.println("Note Not Present");
			return new Response(400, "Note Not Present", token);
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
			return new Response(400, "Invalid Account", token);
		} else if (labels != null) {
			labels.setLabelName(createLabelDto.getLabelName());
			labelRepository.save(labels);
			return new Response(200, "Updated Notes", token);
		} else {
			System.out.println("Note Not Present");
			return new Response(400, "Note Not Present", token);
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
			return new Response(400, "Invalid Account", token);
		} 
		//		else if(user.getNotes()!=null) 
		else if (labels != null) {
			labelRepository.deleteById(id);
			return new Response(200, "Deleted Note Successfully", token);
		} else {
			System.out.println("Note Not Present");
			return new Response(400, "Note Not Present", token);
		}
	}
}
