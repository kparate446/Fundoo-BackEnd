package com.bridgelabz.fandoonotesapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fandoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fandoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fandoonotesapi.model.Notes;
import com.bridgelabz.fandoonotesapi.model.User;
import com.bridgelabz.fandoonotesapi.repository.NotesRepository;
import com.bridgelabz.fandoonotesapi.repository.UserRepository;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.utility.JwtToken;

@Service
public class NoteServices implements Services {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtToken jwtToken;
//	@Autowired
	private User user;
	String message;

	/** Create Note */
	@Override
	public Response createNote(String token, CreateNoteDto createNoteDto) {
		Notes notes = mapper.map(createNoteDto, Notes.class);
		System.out.println("Notes CLASS " + notes);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("User Not Exit");
			return new Response(400, "Invalid Account", token);
		} else if (notes != null) {
			notes.setDiscription(createNoteDto.getDiscription());
			notes.setTitle(createNoteDto.getTitle());
			notesRepository.save(notes);
			return new Response(200, "Created Notes", token);
		} else {
			System.out.println("Note Not Present");
			return new Response(400, "Note Not Present", token);
		}
	}

	/** Updated Note */
	public Response updateNote(String token, UpdateNoteDto updateNoteDto, int id) {
		Notes notes = notesRepository.findById(id);
		System.out.println("Notes CLASS " + notes);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("User Not Exit");
			return new Response(400, "Invalid Account", token);
		} else if (notes != null) {
			notes.setDiscription(updateNoteDto.getDiscription());
			notes.setTitle(updateNoteDto.getTitle());
			notesRepository.save(notes);
			// .delete(ID);
			return new Response(200, "Updated Notes", token);
		} else {
			System.out.println("Note Not Present");
			return new Response(400, "Note Not Present", token);
		}
	}

	/** Deleted Note */
	public Response deleteNote(String token, int id) {
		Notes notes = notesRepository.findById(id);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("User Not Exit");
			return new Response(400, "Invalid Account", token);
		} 
//		else if(user.getNotes()!=null) 
		else if (notes != null) {
			notesRepository.deleteById(id);
			return new Response(200, "Deleted Note Successfully", token);
		} else {
			System.out.println("Note Not Present");
			return new Response(400, "Note Not Present", token);
		}
	}
}
