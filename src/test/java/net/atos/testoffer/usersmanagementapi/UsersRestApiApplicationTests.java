package net.atos.testoffer.usersmanagementapi;

import static net.atos.testoffer.usersmanagementapi.model.APIError.UMERR0001;
import static net.atos.testoffer.usersmanagementapi.model.APIError.UMERR0002;
import static net.atos.testoffer.usersmanagementapi.model.APIError.UMERR0003;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.atos.testoffer.usersmanagementapi.model.User;
import net.atos.testoffer.usersmanagementapi.model.UsersRestApiError;
import net.atos.testoffer.usersmanagementapi.repository.UserRepository;

/**
 * Validation Tests
 * @author elheni
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UsersRestApiApplicationTests {

	private static final String STR_HTTP_LOCALHOST = "http://localhost:";

	private static final String STR_REST_API_USERS = "/rest/api/users";

	@LocalServerPort
	private int port;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		userRepository.deleteAll();
	}
	
	@Test
	void test_GivenUser_WhenRegisterMandatoryFieldIsEmpty_ThanBadRequest() {
		User user = new User();
		user.setUsername("");
		user.setBirthDate("02041983");
		user.setEmail("test@test.fr");
		user.setPassword("MyPassword");
		ResponseEntity<String> result = this.restTemplate.postForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS, user,
				String.class);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);		
	}
	
	@Test
	void test_GivenUser_WhenRegisterCountryCodeIsNotFR_ThanBadRequest() {
		User user = new User();
		user.setUsername("test");
		user.setBirthDate("02041983");
		user.setEmail("test@test.fr");
		user.setPassword("MyPassword");
		user.setCountry("EN");
		ResponseEntity<Object> result = this.restTemplate.postForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS, user,
				Object.class);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(result.getBody()).isInstanceOf(Map.class);
		Map<String, String> errors = (Map<String, String>) result.getBody();
		assertThat(errors).isNotNull().containsOnlyKeys("country");
		assertThat(errors.values()).containsOnly("The user is not a local resident");
		
	}
	
	@Test
	void test_GivenUser_WhenRegisterNotAdult_ThanBadRequest() {
		User user = new User();
		user.setUsername("test");
		LocalDate calculatedBirthDate = LocalDate.now().minusYears(17);
		user.setBirthDate(calculatedBirthDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
		user.setEmail("test@test.fr");
		user.setPassword("MyPassword");
		user.setCountry("FR");
		ResponseEntity<Object> result = this.restTemplate.postForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS, user,
				Object.class);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(result.getBody()).isInstanceOf(Map.class);
		Map<String, String> errors = (Map<String, String>) result.getBody();
		assertThat(errors).isNotNull().containsOnlyKeys("birthDate");
		assertThat(errors.values()).containsOnly("User must be adult");
		
	}
	
	@Test
	void test_GivenUser_WhenRegisterExistingUsername_ThanBadRequest() {
		
		String username = "unittest1";
		User user = User.build().username(username).firstName("Unit")
				.lastName("Test").password("Junit@Pass").birthDate("02041980")
				.email("junit.test@java.fr").phoneNumber("0611111111")
				.country("FR");
		{
			ResponseEntity<User> result = this.restTemplate.postForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS,
					user, User.class);
			assertThat(result).isNotNull();
			assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
		{
			ResponseEntity<UsersRestApiError> result = this.restTemplate
					.postForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS, user, UsersRestApiError.class);
			assertThat(result).isNotNull();
			assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
			UsersRestApiError apiError = result.getBody();
			assertThat(apiError).isNotNull().hasFieldOrPropertyWithValue("errorCode", UMERR0003.toString());
		}
	}
	
	@Test
	void test_GivenUser_WhenRegister_ThanCreateUser() {
		User user = new User();
		user.setUsername("test");
		user.setBirthDate("02041983");
		user.setEmail("test@test.fr");
		user.setPassword("MyPassword");
		user.setCountry("FR");
		ResponseEntity<User> result = this.restTemplate.postForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS, user,
				User.class);
		assertThat(result).isNotNull();
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		User newUser = result.getBody();
		assertThat(newUser).isNotNull();
		assertThat(newUser.getId()).isNotNull();
	}
	
	@Test
	void test_GivenUser_WhenFindByUserName_ThanReturnUser() {
		String username = "unittest1";
		{
			User user = User.build().username(username).firstName("Unit")
					.lastName("Test").password("Junit@Pass").birthDate("02041980")
					.email("junit.test@java.fr").phoneNumber("0611111111")
					.country("FR");
			ResponseEntity<User> result = this.restTemplate.postForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS, user,
					User.class);		
			assertThat(result).isNotNull();
			assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
		{
			ResponseEntity<User> result = this.restTemplate.getForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS+"/?username="+username, 
					User.class);	
			assertThat(result).isNotNull();
			assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
			User newUser = result.getBody();
			assertThat(newUser).isNotNull();
			assertThat(newUser.getId()).isNotNull();
			assertThat(newUser.getUsername()).isEqualTo(username);
		}
	}
	
	@Test
	void test_GivenUser_WhenFindByUserName_ThanNoUserFound() {
		{
			ResponseEntity<UsersRestApiError> result = this.restTemplate.getForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS + "/?username=nouserfound", 
					UsersRestApiError.class);	
			assertThat(result).isNotNull();
			assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
			UsersRestApiError apiError = result.getBody();
			assertThat(apiError).isNotNull().hasFieldOrPropertyWithValue("errorCode", UMERR0001.toString());
		}
	}
	
	@Test
	void test_GivenUser_WhenFindByUserId_ThanReturnUser() {
		String userId = null;
		{
			User user = User.build().username("unittest1").firstName("Unit")
					.lastName("Test").password("Junit@Pass").birthDate("02041980")
					.email("junit.test@java.fr").phoneNumber("0611111111")
					.country("FR");
			ResponseEntity<User> result = this.restTemplate.postForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS, user,
					User.class);		
			assertThat(result).isNotNull();
			assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
			User createdUser = result.getBody();
			assertThat(createdUser).isNotNull();
			userId = createdUser.getId();
		}
		{
			ResponseEntity<User> result = this.restTemplate.getForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS + "/"+String.valueOf(userId), 
					User.class);	
			assertThat(result).isNotNull();
			assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
			User newUser = result.getBody();
			assertThat(newUser).isNotNull();
			assertThat(newUser.getId()).isNotNull().isEqualTo(userId);
		}
	}
	
	@Test
	void test_GivenUser_WhenFindByUserId_ThanNoUserFound() {
		{
			ResponseEntity<UsersRestApiError> result = this.restTemplate.getForEntity(STR_HTTP_LOCALHOST + port + STR_REST_API_USERS + "/2", 
					UsersRestApiError.class);	
			assertThat(result).isNotNull();
			assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
			UsersRestApiError apiError = result.getBody();
			assertThat(apiError).isNotNull().hasFieldOrPropertyWithValue("errorCode", UMERR0002.toString());
		}
	}
	
}
