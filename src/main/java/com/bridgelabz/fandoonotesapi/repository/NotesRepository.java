package com.bridgelabz.fandoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.fandoonotesapi.model.Notes;
import com.bridgelabz.fandoonotesapi.model.User;

public interface NotesRepository extends JpaRepository<Notes, Object> {
	Notes findById(int Id); 
}
