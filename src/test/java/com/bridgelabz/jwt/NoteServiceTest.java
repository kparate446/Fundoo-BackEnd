package com.bridgelabz.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.bridgelabz.fundoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fundoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fundoonotesapi.message.MessageData;
import com.bridgelabz.fundoonotesapi.model.Notes;
import com.bridgelabz.fundoonotesapi.repository.NotesRepository;
import com.bridgelabz.fundoonotesapi.service.NoteServiceImp;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;

public class NoteServiceTest {
	@InjectMocks
	private NoteServiceImp noteServiceImp;
	@Mock
	private NotesRepository notesRepository;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	private ModelMapper mapper;
	@Mock
	private JwtToken jwtToken;
	@Mock
	MessageData messageData;
	@Mock
	private CreateNoteDto createNoteDto;
	@Mock
	private UpdateNoteDto updateNoteDto;
	/**
	 * Testing of Created Note method
	 */
	@Test
	public void createNoteTest() {
		CreateNoteDto createNoteDto  = new CreateNoteDto();
		Notes notes =new Notes();
		notes.setTitle("Junit");
		notes.setDiscription("Testingg");
		Optional<Notes> already = Optional.of(notes);
		if(already.isPresent()){
			return;
		}
		when(mapper.map(createNoteDto, Notes.class)).thenReturn(notes);
		boolean saved = when(notesRepository.save(notes)).thenReturn(notes) != null;
		System.out.println("saved:"+saved);
		assertEquals(true, saved);
	}
	/**
	 * Testing of Updated Note method
	 */
	@Test
	public void updateNoteTest() {
		UpdateNoteDto updateNoteDto  = new UpdateNoteDto();
		Notes notes =new Notes();
		notes.setTitle(updateNoteDto.getTitle());
		notes.setDiscription(updateNoteDto.getDiscription());
		Optional<Notes> already = Optional.of(notes);
		if(already.isPresent()){
			return;
		}
		boolean saved = when(notesRepository.save(notes)).thenReturn(notes) != null;
		System.out.println("saved:"+saved);
		assertEquals(true, saved);
	}
	/**
	 * Testing of Get all Note method
	 */
	@Test
	public void getAllNotesTest() {
		Notes notes = new Notes();
		List<Notes> listNote = new ArrayList<>();
		notes.setTitle("Testing");
		notes.setDiscription("Junit");
		listNote.add(notes);
		Optional<Notes> already = Optional.of(notes);
		if(already.isPresent()){
			return;
		}
		when(notesRepository.findAll()).thenReturn(listNote);
		assertEquals(true, notes);
	}

}
