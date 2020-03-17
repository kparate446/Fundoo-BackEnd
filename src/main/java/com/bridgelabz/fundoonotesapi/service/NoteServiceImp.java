package com.bridgelabz.fundoonotesapi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fundoonotesapi.dto.ReminderDto;
import com.bridgelabz.fundoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fundoonotesapi.exception.InvalidNoteException;
import com.bridgelabz.fundoonotesapi.exception.InvalidOrderException;
import com.bridgelabz.fundoonotesapi.exception.InvalidReminderException;
import com.bridgelabz.fundoonotesapi.exception.InvalidTitleException;
import com.bridgelabz.fundoonotesapi.exception.InvalidUserException;
import com.bridgelabz.fundoonotesapi.exception.ReminderAlreadyPresentException;
import com.bridgelabz.fundoonotesapi.message.MessageData;
import com.bridgelabz.fundoonotesapi.model.Notes;
import com.bridgelabz.fundoonotesapi.model.Reminder;
import com.bridgelabz.fundoonotesapi.model.User;
import com.bridgelabz.fundoonotesapi.repository.NotesRepository;
import com.bridgelabz.fundoonotesapi.repository.ReminderRepository;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;
import com.sun.istack.logging.Logger;
/**
 * @author :- Krunal Parate Purpose :- Implementing the API
 */
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
	@Autowired
	ElasticSearchServiceImp elastic;
	@Autowired
	private MessageData messageData;
	private User user;
	@Autowired
	private ReminderRepository reminderRepository;
	@Autowired 
	ElasticSearchServiceImp elasticSearchServiceImp;
	
	String message;
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImp.class);

	/** Create Note 
	 * @throws Exception */
	public Response createNote(String token, CreateNoteDto createNoteDto) throws Exception {
		Notes notes = mapper.map(createNoteDto, Notes.class);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (notes != null) {
			notes.setDiscription(createNoteDto.getDiscription());
			notes.setTitle(createNoteDto.getTitle());
			notes.setUser(user);
			elasticSearchServiceImp.createNote(notes);
			notesRepository.save(notes);
			LOGGER.info("Created note Succesfully in Note table");
			return new Response(200, "Created Notes", true);
		} else {
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
	}

	/** Updated Note */
	public Response updateNote(String token, UpdateNoteDto updateNoteDto, int id) {
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (notes != null) {
			notes.setDiscription(updateNoteDto.getDiscription());
			notes.setTitle(updateNoteDto.getTitle());
			notesRepository.save(notes);
			LOGGER.info("Successfully Note Updated");
			return new Response(200, "Updated Notes", true);
		} else {
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
	}

	/** Deleted Note */
	public Response deleteNote(String token, int id) {
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (notes == null) {
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
		if((notes.getUser().getId() != user.getId()))
		{
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		} else {
			notesRepository.deleteById(id);
			LOGGER.info("Successfully Deleted Note");
			return new Response(200, "Deleted Note Successfully", true);
		}
	}

	/** Show All Notes */
	public Response getNotes(String token) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			List<Notes> note = notesRepository.findAll().stream().filter(e -> e.getUser().getId() == user.getId())
					.collect(Collectors.toList());
			LOGGER.info("Successfully showing the Notes table data");
			return new Response(200, "Show the All Notes Successfully ", note);
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Sort By Title */
	public Response sortByTitle(String token, String order) {
		String email = jwtToken.getToken(token);
		String Desc = "desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			if (Asc.equals(order)) {
				//				List<Notes> note = notesRepository.findAll().stream()
				List<Notes> note =user.getNotes().stream()
						.sorted((list1, list2) -> list1.getTitle().compareTo(list2.getTitle()))
						.collect(Collectors.toList());
				LOGGER.info("Successfully Sorted By Title in Ascending Order");
				return new Response(200, "Sorted By Title in Ascending Order ", note);
			}
			if (Desc.equals(order)) {
				List<Notes> note = user.getNotes().stream()
						.sorted((list2, list1) -> list1.getTitle().compareTo(list2.getTitle()))
						.collect(Collectors.toList());
				LOGGER.info("Successfully Sorted By Title in Descending Order");
				return new Response(200, "Sorted By Title in Descending Order ", note);
			} else {
				LOGGER.warning("Please choose the correct Order");
				throw new InvalidOrderException(messageData.Invalid_Order);
			}
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Sort By Description */
	public Response sortByDescription(String token, String order) {
		String email = jwtToken.getToken(token);
		String Desc = "desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			if (Asc.equals(order)) {
				List<Notes> notes = user.getNotes().stream()
						.sorted((list1, list2) -> list1.getDiscription().compareTo(list2.getDiscription()))
						.collect(Collectors.toList());
				LOGGER.info("Successfully Sorted By Description in Ascending Order");
				return new Response(200, "Sorted By Description in Ascending Order ", notes);
			}
			if (Desc.equals(order)) {
				List<Notes> notes = user.getNotes().stream()
						.sorted((list2, list1) -> list1.getDiscription().compareTo(list2.getDiscription()))
						.collect(Collectors.toList());
				LOGGER.info("Successfully Sorted By Description in Descending Order");
				return new Response(200, "Sorted By Description in Descending Order ", notes);
			} else {
				LOGGER.warning("Please choose the correct Order");
				throw new InvalidOrderException(messageData.Invalid_Order);
			}
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Sort By Date */
	public Response sortByDate(String token, String order) {
		String email = jwtToken.getToken(token);
		String Desc = "desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			if (Asc.equals(order)) {
				List<Notes> notes = user.getNotes().stream()
						.sorted((list1, list2) -> list1.getDate().compareTo(list2.getDate()))
						.collect(Collectors.toList());
				LOGGER.info("Successfully Sorted By Date in Ascending Order");
				return new Response(200, "Sorted By Date in Ascending Order ", notes);
			}
			if (Desc.equals(order)) {
				List<Notes> notes = user.getNotes().stream()
						.sorted((list2, list1) -> list1.getDate().compareTo(list2.getDate()))
						.collect(Collectors.toList());
				LOGGER.info("Successfully Sorted By Date in Descending Order");
				return new Response(200, "Sorted By Date in Descending Order ", notes);
			} 
			LOGGER.warning("Please choose the correct Order");
			throw new InvalidOrderException(messageData.Invalid_Order);
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Pin & Unpin Notes */
	public Response pinNotes(String token, int id) {
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidNoteException(messageData.Invalid_User);
		}
		if (notes != null) {
			if (notes.getUser().getId() == user.getId()) {
				if (notes.isPin() == false) {
					notes.setPin(true);
					notesRepository.save(notes);
					LOGGER.info("Successfully Pin the Note");
					return new Response(200, "Pin Note ", notes);
				} else {
					notes.setPin(false);
					notesRepository.save(notes);
					LOGGER.info("Successfully Unpin the Note");
					return new Response(200, "Unpin Note", notes);
				}
			}
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Trashed & Restore Notes */
	public Response trashedNotes(String token, int id) {
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidNoteException(messageData.Invalid_User);
		}
		if (notes!= null) {
			if (notes.getUser().getId() == user.getId()) {//
				if (notes.isTrash() == false) {
					notes.setTrash(true);
					notesRepository.save(notes);
					LOGGER.info("Successfully Trashed the Note");
					return new Response(200, "Trashed Note ", notes);
				} else {
					notes.setTrash(false);
					notesRepository.save(notes);
					LOGGER.info("Successfully Restored the Note");
					return new Response(200, "Restored Note", notes);
				}
			}
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Archive & Unarchive */
	public Response archiveNotes(String token, int id) {
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidNoteException(messageData.Invalid_User);
		}
		if (notes != null) {
			if (notes.getUser().getId() == user.getId()) {//
				if (notes.isAchieve() == false) {
					notes.setAchieve(true);
					notesRepository.save(notes);
					LOGGER.info("Successfully Archived the Note");
					return new Response(200, "Archived Note ", notes);
				} else {
					notes.setAchieve(false);
					notesRepository.save(notes);
					LOGGER.info("Successfully unarchived the note");
					return new Response(200, "Unarchived Note ", notes);
				}
			}
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Searching the notes Based on the Id */
	public Response findById(String token, int id) {
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (notes != null) {
			if (notes.getUser().getId() == user.getId()) {
				LOGGER.info("Successfully Searching the notes Based on the Id");
				return new Response(200, " Searching the notes Based on the Id ", notesRepository.findById(id));// notesRepository.findById(id)
			}
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Add Reminder */
	public Response createReminder(String token, ReminderDto reminderDto, int id) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new InvalidNoteException(messageData.Invalid_Note));
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (notes == null) {
			LOGGER.warning("Note not present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
		if (notes.getReminder() == null) {
			Reminder reminder = mapper.map(reminderDto, Reminder.class);
			reminder.setDateAndTime(reminderDto.getDateAndTime());
			reminder.setNotes(notes);// Add the User Id
			reminderRepository.save(reminder);
			LOGGER.info("Successfully Reminder Added");
			return new Response(200, "Reminder Successfully Added", true);
		} else {
			LOGGER.warning("Note not present");
			throw new ReminderAlreadyPresentException(messageData.Reminder_Already_Present);
		}
	}

	/** Delete Reminder **/
	public Response deleteReminder(String token, int id) {
		String email = jwtToken.getToken(token);
		Reminder reminder = reminderRepository.findById(id).orElseThrow(() -> new InvalidReminderException(messageData.Invalid_Reminder));
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (reminderRepository.findById(id) != null) {
			//			reminderRepository.deleteById(reminderRepository.findById(id).getId());
			reminderRepository.deleteById(reminder.getId());
			LOGGER.info("Successfully Reminder Deleted");
			return new Response(200, "Reminder Deleted Successfully", true);
		} else {
			LOGGER.warning("Invalid Reminder");
			throw new InvalidReminderException(messageData.Invalid_Reminder);
		}
	}

	/** Update Reminder **/
	@SuppressWarnings("unused")
	public Response updateReminder(String token, ReminderDto reminderDto, int id) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		Reminder reminder = reminderRepository.findById(id).orElseThrow(() -> new InvalidReminderException(messageData.Invalid_Reminder));
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (reminder == null) {
			LOGGER.warning("Invalid Reminder");
			throw new InvalidReminderException(messageData.Invalid_Reminder);
		}
		if (reminder != null) {
			reminder.setDateAndTime(reminderDto.getDateAndTime());
			reminderRepository.save(reminder);
			LOGGER.info("Successfully Updated Reminder");
			return new Response(200, "Updated Reminder", true);
		}
		LOGGER.warning("Invalid Reminder");
		throw new InvalidReminderException(messageData.Invalid_Reminder);
	}

	/** Show Reminder **/
	public Response getReminder(String token) {
		// Notes notes = (Notes) notesRepository.findAll();
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			List<Reminder> reminder = reminderRepository.findAll();
			LOGGER.info("Successfully showing the Reminder table data");
			return new Response(200, "Show the All Reminder Successfully ", reminder);
		}
		LOGGER.warning("Invalid Reminder");
		throw new InvalidReminderException(messageData.Invalid_Reminder);
	}

	/** Searching the notes Based on the Title **/
	public Response findByTitle(String token, String title) {
		//				List<Notes> notes= notesRepository.findAll();
		Notes notes = notesRepository.findByTitle(title);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {	
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (notes != null) {
			// It is Used in Title Search for letters
			//			List<Notes>list = notes.stream().filter(note ->  { 
			//				if (note.getTitle().contains(title));
			//				return true;
			//			}).collect(Collectors.toList());
			//			return new Response(200," Searching the notes Based on the Title",list);
			if (notes.getTitle().equals(title)) {
				LOGGER.info("Successfully Searching the notes based on the Title");
				return new Response(200, " Searching the notes based on the Title", notesRepository.findByTitle(title));// notesRepository.findById(id)
			} else {
				LOGGER.warning("Title Not Present");
				throw new InvalidTitleException(messageData.Invalid_Title);
			}
		}
		LOGGER.warning("Note not present");
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Searching the notes Based on the Discription */
	public Response findByDiscription(String token, String discription) {
		Notes notes= notesRepository.findByDiscription(discription);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {	
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (notes != null) {
			if (notes.getDiscription().equals(discription)) {
				LOGGER.info("Successfully Searching the notes based on the Discription");
				return new Response(200, " Searching the notes based on the Discription", notesRepository.findByDiscription(discription));
			} else {
				LOGGER.warning("Discription not present");
				return new Response(400, "Discription not present", false);
			}
		}
		LOGGER.warning("Discription not present");
		return new Response(400, "Discription not present", false);
	}
}