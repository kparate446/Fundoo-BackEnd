package com.bridgelabz.fundoonotesapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotesapi.model.Labels;
@Repository
public interface LabelsRepository extends JpaRepository<Labels, Object> {
}
