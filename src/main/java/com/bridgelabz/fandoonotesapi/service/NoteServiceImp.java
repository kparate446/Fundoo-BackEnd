package com.bridgelabz.fandoonotesapi.service;

import java.util.List;
import java.util.stream.Collectors;
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
public class NoteServiceImp implements NoteService {
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
			notes.setUser(user);//
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
		if (notes == null) { 
			return new Response(200, "Deleted Note Not present ", token);
		} 
		if(notes.getUser().getId()== user.getId()){/***/
			notesRepository.deleteById(id);
			return new Response(200, "Deleted Note Successfully", token);
		}
		else {
			System.out.println("Note Not Present");
			return new Response(400, "This Note does Not Belongs to Present", token);
		}
	}
	/**Show All Notes*/
	public Response getNotes(String token){
		//Notes notes = (Notes) notesRepository.findAll();
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if(user == null) {
			System.out.println("User Not Exit");
			new Response(400, "Invalid Account", token);
		} if(user.getNotes()!=null) {
			List<Notes>note = notesRepository.findAll().stream().filter(e ->e.getUser().getId()==user.getId()).collect(Collectors.toList());
			return	new Response(200, "Show the All Notes Successfully ", note);
		}
		return  new Response(400, "Note Not Present", token);
	}
	/**Sort By Title */
	public Response sortByTitle(String token,String order){
		//Notes notes = (Notes) notesRepository.findAll();
		String email = jwtToken.getToken(token);
		String Desc="desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if(user == null) {
			System.out.println("User Not Exit");
			new Response(400, "Invalid Account", token);
		} if(user.getNotes()!= null) {
			if(Asc.equals(order)) {
			List<Notes>note = notesRepository.findAll().stream().sorted((list1,list2)->list1.getTitle().compareTo(list2.getTitle())).collect(Collectors.toList());
			//			List<Notes>note = notesRepository.findAll().stream().sorted((list2,list1)->list1.getTitle().compareTo(list2.getTitle())).collect(Collectors.toList());
			return	new Response(200, "Sorted By Title in Ascending Order ", note);
			}if(Desc.equals(order)) {
				List<Notes>note = notesRepository.findAll().stream().sorted((list2,list1)->list1.getTitle().compareTo(list2.getTitle())).collect(Collectors.toList());
				return	new Response(200, "Sorted By Title in Descending Order ", note);
			}
		}
		return new Response(400, "Note Not Present", token);
	}
	/** Sort By Description */
	public Response sortByDescription(String token,String order) {
		String email = jwtToken.getToken(token);
		String Desc="desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if(user == null) {
			new Response(400, "Invalid Account", token);
		}	
		if(user.getNotes()!=null) {
			if(Asc.equals(order)) {
			List<Notes>notes = notesRepository.findAll().stream().sorted((list1,list2)->list1.getDiscription().compareTo(list2.getDiscription()
					)).collect(Collectors.toList());
			return new Response(200, "Sorted By Description in Ascending Order ", notes);
			}
			if(Desc.equals(order)) {
				List<Notes>notes = notesRepository.findAll().stream().sorted((list2,list1)->list1.getDiscription().compareTo(list2.getDiscription()
						)).collect(Collectors.toList());
				return new Response(200, "Sorted By Description in Descending Order ", notes);
			}
		}
		return new Response(400, "Note not present", token);
	}
	/** Sort By Date */
	public Response sortByDate(String token,String order) {
		String email = jwtToken.getToken(token);
		String Desc="desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if(user == null) {
			new Response(400, "Invalid Account", token);
		}	
		if(user.getNotes()!=null) {
			if(Asc.equals(order)) {
			List<Notes>notes = notesRepository.findAll().stream().sorted((list1,list2)->list1.getDate().compareTo(list2.getDate())).collect(Collectors.toList());
			return new Response(200, "Sorted By Date in Ascending Order ", notes);
			}
			if(Desc.equals(order)) {
				List<Notes>notes = notesRepository.findAll().stream().sorted((list2,list1)->list1.getDate().compareTo(list2.getDate())).collect(Collectors.toList());
				return new Response(200, "Sorted By Date in Descending Order ", notes);
			}
		}
		return new Response(400, "Note not present", token);
	}
}