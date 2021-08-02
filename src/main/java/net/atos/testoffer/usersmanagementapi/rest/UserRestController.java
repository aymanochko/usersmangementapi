package net.atos.testoffer.usersmanagementapi.rest;


import static net.atos.testoffer.usersmanagementapi.model.APIError.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.atos.testoffer.usersmanagementapi.model.User;
import net.atos.testoffer.usersmanagementapi.model.UsersRestApiError;
import net.atos.testoffer.usersmanagementapi.repository.UserRepository;

/**
 * Implementation of the rest Services
 * @author elheni
 *
 */
@RestController
@RequestMapping(path = "/rest/api/", produces = "application/json")
public class UserRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Get Service that returns a @User given a technical Id
	 * @param id Technical Identifier of the user
	 * @return @ResponseEntity<@Object>
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<Object> user(@PathVariable String id){
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isEmpty()) {
			LOGGER.debug("GET /user/{} : RESULT no user found with id {}", id, id);
			return ResponseEntity.ok().body(UsersRestApiError.build().errorCode(UMERR0002.toString()).errorMessage(UMERR0002.getErrorDescription()));
		}
		
		LOGGER.debug("GET /user/{} : RESULT user with id {} returned",id, id);
		return ResponseEntity.ok(optionalUser.get());
	}
	
	/**
	 * Get Service that returns a @User given a username
	 * @param username the username of the user
	 * @return @ResponseEntity<@Object>
	 */
	@GetMapping("/users")
	public ResponseEntity<Object> userByUsername(@RequestParam(defaultValue = "") String username){
		List<User> usersList = userRepository.findByUsername(username);
		if(Objects.isNull(usersList) || usersList.isEmpty()) {
			LOGGER.debug("GET /user?username={} : RESULT no user found with username {}", username, username);
			return ResponseEntity.ok().body(UsersRestApiError.build().errorCode(UMERR0001.toString()).errorMessage(UMERR0001.getErrorDescription()));
		}
		
		return ResponseEntity.ok(usersList.iterator().next());
	}
	
	/**
	 * Post Service that validate and create a user in the database
	 * @param user @User submitted
	 * @return ResponseEntity<Object>
	 */
	@PostMapping("/users")
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user){
		List<User> userList = userRepository.findByUsername(user.getUsername());
		if(!Objects.isNull(userList) && !userList.isEmpty()) {
			return ResponseEntity.badRequest().body(UsersRestApiError.build().errorCode(UMERR0003.toString()).errorMessage(UMERR0003.getErrorDescription()));
		}
		return ResponseEntity.ok(userRepository.save(user));
	}

	/**
	 * Return a 400 Http code and errors when sumitted user failed the validation process
	 * @param ex @MethodArgumentNotValidException 
	 * @return @Map<@String, @String>
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach(error -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
