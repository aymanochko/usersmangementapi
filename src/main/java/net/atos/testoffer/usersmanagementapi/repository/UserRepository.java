package net.atos.testoffer.usersmanagementapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.atos.testoffer.usersmanagementapi.model.User;
/**
 * User Repository that offer the possibility to store data using JPA
 * 
 * @author elheni
 *
 */
public interface UserRepository extends MongoRepository<User, String> {

	/**
	 * Find a user from the username
	 * @param username
	 * @return List of the users found
	 */
	List<User> findByUsername(String username); 
	
}
