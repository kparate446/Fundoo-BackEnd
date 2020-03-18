package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotesapi.model.Collabrator;
@Repository
public interface CollabratorRepository extends JpaRepository<Collabrator, Object> {
}

