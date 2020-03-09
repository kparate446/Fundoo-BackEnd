package com.bridgelabz.fandoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.fandoonotesapi.model.Collabrator;

public interface CollabratorRepository extends JpaRepository<Collabrator, Object>{
	Collabrator findById(int Id); 
}
