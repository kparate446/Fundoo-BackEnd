package com.bridgelabz.fandoonotesapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.fandoonotesapi.model.User;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Interface of JPA-->Storing ,Accessing & Managing JAVA object in a relational Database
 *  //JPA Repository
 */
public interface UserRepository extends JpaRepository<User, Object> {
	User findByEmail(String email);
}
