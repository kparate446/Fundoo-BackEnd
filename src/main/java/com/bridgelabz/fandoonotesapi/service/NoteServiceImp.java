package com.bridgelabz.fandoonotesapi.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fandoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fandoonotesapi.dto.ReminderDto;
import com.bridgelabz.fandoonotesapi.dto.UpdateNoteDto;
import com.bridgelabz.fandoonotesapi.exception.InvalidNoteException;
import com.bridgelabz.fandoonotesapi.exception.InvalidOrderException;
import com.bridgelabz.fandoonotesapi.exception.InvalidReminderException;
import com.bridgelabz.fandoonotesapi.exception.InvalidUserException;
import com.bridgelabz.fandoonotesapi.exception.ReminderAlreadyPresentException;
import com.bridgelabz.fandoonotesapi.message.MessageData;
import com.bridgelabz.fandoonotesapi.model.Notes;
import com.bridgelabz.fandoonotesapi.model.Reminder;
import com.bridgelabz.fandoonotesapi.model.User;
import com.bridgelabz.fandoonotesapi.repository.NotesRepository;
import com.bridgelabz.fandoonotesapi.repository.ReminderRepository;
import com.bridgelabz.fandoonotesapi.repository.UserRepository;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.utility.JwtToken;
/**
 * @author :- Krunal Parate 
 * Purpose :- Implementing the API
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
	private MessageData messageData;
	// @Autowired
	private User user;
	@Autowired
	private ReminderRepository reminderRepository;
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
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (notes != null) {
			notes.setDiscription(createNoteDto.getDiscription());
			notes.setTitle(createNoteDto.getTitle());
			notes.setUser(user);//
			notesRepository.save(notes);
			return new Response(200, "Created Notes", token);
		} else {
			System.out.println("Note Not Present");
			throw new InvalidNoteException(messageData.Invalid_Note);
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
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (notes != null) {
			notes.setDiscription(updateNoteDto.getDiscription());
			notes.setTitle(updateNoteDto.getTitle());
			notesRepository.save(notes);
			// .delete(ID);
			return new Response(200, "Updated Notes", token);
		} else {
			System.out.println("Note Not Present");
			throw new InvalidNoteException(messageData.Invalid_Note);
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
			throw new InvalidUserException(messageData.Invalid_User);
		}
		// else if(user.getNotes()!=null)
		if (notes == null) {
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
		if (notes.getUser().getId() == user.getId()) {/***/
			notesRepository.deleteById(id);
			return new Response(200, "Deleted Note Successfully", token);
		} else {
			System.out.println("This Note does Not Belongs to Present");
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
	}

	/** Show All Notes */
	public Response getNotes(String token) {
		// Notes notes = (Notes) notesRepository.findAll();
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			List<Notes> note = notesRepository.findAll().stream().filter(e -> e.getUser().getId() == user.getId())
					.collect(Collectors.toList());
			return new Response(200, "Show the All Notes Successfully ", note);
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Sort By Title */
	public Response sortByTitle(String token, String order) {
		// Notes notes = (Notes) notesRepository.findAll();
		String email = jwtToken.getToken(token);
		String Desc = "desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			if (Asc.equals(order)) {
				List<Notes> note = notesRepository.findAll().stream()
						.sorted((list1, list2) -> list1.getTitle().compareTo(list2.getTitle()))
						.collect(Collectors.toList());
				// List<Notes>note =
				// notesRepository.findAll().stream().sorted((list2,list1)->list1.getTitle().compareTo(list2.getTitle())).collect(Collectors.toList());
				return new Response(200, "Sorted By Title in Ascending Order ", note);
			}
			if (Desc.equals(order)) {
				List<Notes> note = notesRepository.findAll().stream()
						.sorted((list2, list1) -> list1.getTitle().compareTo(list2.getTitle()))
						.collect(Collectors.toList());
				return new Response(200, "Sorted By Title in Descending Order ", note);
			} else {
				throw new InvalidOrderException(messageData.Invalid_Order);
			}
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Sort By Description */
	public Response sortByDescription(String token, String order) {
		String email = jwtToken.getToken(token);
		String Desc = "desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			if (Asc.equals(order)) {
				List<Notes> notes = notesRepository.findAll().stream()
						.sorted((list1, list2) -> list1.getDiscription().compareTo(list2.getDiscription()))
						.collect(Collectors.toList());
				return new Response(200, "Sorted By Description in Ascending Order ", notes);
			}
			if (Desc.equals(order)) {
				List<Notes> notes = notesRepository.findAll().stream()
						.sorted((list2, list1) -> list1.getDiscription().compareTo(list2.getDiscription()))
						.collect(Collectors.toList());
				return new Response(200, "Sorted By Description in Descending Order ", notes);
			} else {
				throw new InvalidOrderException(messageData.Invalid_Order);
			}
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Sort By Date */
	public Response sortByDate(String token, String order) {
		String email = jwtToken.getToken(token);
		String Desc = "desc";
		String Asc = "asc";
		user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			if (Asc.equals(order)) {
				List<Notes> notes = notesRepository.findAll().stream()
						.sorted((list1, list2) -> list1.getDate().compareTo(list2.getDate()))
						.collect(Collectors.toList());
				return new Response(200, "Sorted By Date in Ascending Order ", notes);
			}
			if (Desc.equals(order)) {
				List<Notes> notes = notesRepository.findAll().stream()
						.sorted((list2, list1) -> list1.getDate().compareTo(list2.getDate()))
						.collect(Collectors.toList());
				return new Response(200, "Sorted By Date in Descending Order ", notes);
			} else {
				throw new InvalidOrderException(messageData.Invalid_Order);
			}
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Pin & Unpin Notes */
	public Response pinNotes(String token, int id) {
		Notes notes = notesRepository.findById(id);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidNoteException(messageData.Invalid_User);
		}
		if (notes != null) {
			if (notes.getUser().getId() == user.getId()) {
				if (notes.isPin() == false) {
					notes.setPin(true);
					notesRepository.save(notes);
					return new Response(200, "Pin Note ", notes);
				} else {
					notes.setPin(false);
					notesRepository.save(notes);
					return new Response(200, "Unpin Note", notes);
				}
			}
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Trashed & Restore Notes */
	public Response trashedNotes(String token, int id) {
		Notes notes = notesRepository.findById(id);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(user.getId());
		if (user == null) {
			throw new InvalidNoteException(messageData.Invalid_User);
		}
		if (notes != null) {
			if (notes.getUser().getId() == user.getId()) {//
				if (notes.isTrash() == false) {
					notes.setTrash(true);
					notesRepository.save(notes);
					return new Response(200, "Trashed Note ", notes);
				} else {
					notes.setTrash(false);
					notesRepository.save(notes);
					return new Response(200, "Restored Note", notes);
				}
			}
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Archive & Unarchive */
	public Response archiveNotes(String token, int id) {
		Notes notes = notesRepository.findById(id);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			throw new InvalidNoteException(messageData.Invalid_User);
		}
		if (notes != null) {
			if (notes.getUser().getId() == user.getId()) {//
				if (notes.isAchieve() == false) {
					notes.setAchieve(true);
					notesRepository.save(notes);
					return new Response(200, "Archive Note ", notes);
				} else {
					notes.setAchieve(false);
					notesRepository.save(notes);
					return new Response(200, "Unarchive Note ", notes);
				}
			}
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}

	/** Searching the notes Based on the Id */
	public Response findById(String token, int id) {
		Notes notes = notesRepository.findById(id);
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (notes != null) {
			if (notes.getUser().getId() == user.getId()) {
				return new Response(200, " Searching the notes Based on the Id ", notesRepository.findById(id));// notesRepository.findById(id)
			}
		}
		throw new InvalidNoteException(messageData.Invalid_Note);
	}
	/**
	 * Searching the notes Based on the Title @SuppressWarnings("null") 
	 * public Response findByTitle(String token, String title) {
	 *  Notes notes = null;
	 *   String email = jwtToken.getToken(token);
	 *    user = userRepository.findByEmail(email);
	 * if(user == null) { 
	 * throw new InvalidUserException(messageData.Invalid_User);
	 * } 
	 * if(user.getNotes()!=null) {
	 * if(notes.getTitle().equals(title)) {
	 *  } }
	 *   return null; 
	 *   }
	 */

	/** Add Reminder */
	public Response createReminder(String token, ReminderDto reminderDto, int id) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		Notes notes = notesRepository.findById(id);
		System.out.println(token);
		if (user == null) {
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if(notes ==null) {
			throw new InvalidNoteException(messageData.Invalid_Note);
		}
		if (notes.getReminder() == null) {
			Reminder reminder= mapper.map(reminderDto, Reminder.class);
			reminder.setDateAndTime(reminderDto.getDateAndTime());
			reminder.setNotes(notes);// Add the User Id
			reminderRepository.save(reminder);
			return new Response(200, "Reminder", "Success");
		} else {
			throw new ReminderAlreadyPresentException(messageData.Reminder_Already_Present);
		}
	}
	/** Delete Reminder*/
	public Response deleteReminder(String token,int id) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			System.out.println("Note Not Present");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (reminderRepository.findById(id) != null) {
			reminderRepository.deleteById(reminderRepository.findById(id).getId());
			return new Response(200, "Reminder Deleted Successfully", "Success");
		} else {
			throw new InvalidReminderException(messageData.Invalid_Reminder);
		}
	}
	/** Update Reminder*/
	@SuppressWarnings("unused")
	public Response updateReminder(String token, ReminderDto reminderDto, int id) {
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		Reminder reminder = reminderRepository.findById(id);
		System.out.println(token);
		if (user == null) {
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if(reminder ==null) {
			throw new InvalidReminderException(messageData.Invalid_Reminder);
		}
		if (reminder!= null) {
			reminder.setDateAndTime(reminderDto.getDateAndTime());
			reminderRepository.save(reminder);
			return new Response(200, "Updated Reminder", "Successfully");
		}
		throw new InvalidReminderException(messageData.Invalid_Reminder);
	}
	/** Show Reminder*/
	public Response getReminder(String token) {
		// Notes notes = (Notes) notesRepository.findAll();
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.getNotes() != null) {
			List<Reminder> reminder = reminderRepository.findAll();
			return new Response(200, "Show the All Reminder Successfully ", reminder);
		}
		throw new InvalidReminderException(messageData.Invalid_Reminder);
	}
}