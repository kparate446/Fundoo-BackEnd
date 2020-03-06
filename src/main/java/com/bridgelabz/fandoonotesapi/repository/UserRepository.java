package com.bridgelabz.fandoonotesapi.repository;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Interface of JPA-->Storing ,Accessing & Managing JAVA object in a relational Database
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fandoonotesapi.model.User;
// JPA Repository
public interface UserRepository extends JpaRepository<User, Object> {
	User findByEmail(String email);
}
