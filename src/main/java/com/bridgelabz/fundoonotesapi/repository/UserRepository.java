package com.bridgelabz.fundoonotesapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotesapi.model.User;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Interface of JPA-->Storing ,Accessing & Managing JAVA object in a relational Database
 *  //JPA Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Object> {
	User findByEmail(String email);
}
