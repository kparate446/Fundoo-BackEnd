package com.bridgelabz.fandoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.fandoonotesapi.model.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Object>{
	Reminder findById(int Id); 
}
