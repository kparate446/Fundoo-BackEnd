package com.bridgelabz.fandoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.fandoonotesapi.model.Labels;

public interface LabelsRepository extends JpaRepository<Labels, Object> {
	Labels findById(int Id);
}
