package com.bridgelabz.user.repository;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Interface of JPA-->Storing ,Accessing & Managing JAVA object in a relational Database
 */
import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.user.model.User;
// JPA Repository
public interface UserRepository extends JpaRepository<User, Object> {
	User findByemail(String email); 
}
