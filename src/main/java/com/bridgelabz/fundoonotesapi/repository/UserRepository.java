package com.bridgelabz.fundoonotesapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bridgelabz.fundoonotesapi.model.User;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Interface of JPA-->Storing ,Accessing & Managing JAVA object in a relational Database
 *  //JPA Repository
 */
//@CrossOrigin("http://localhost:3000")
@Repository
public interface UserRepository extends JpaRepository<User, Object> {
	User findByEmail(String email);
}
