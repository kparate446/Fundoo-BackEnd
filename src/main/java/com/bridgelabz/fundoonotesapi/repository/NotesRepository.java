package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotesapi.model.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Object> {
	Notes findByTitle(String title);
	Notes findByDiscription(String discription);
}
