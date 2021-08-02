package net.atos.testoffer.usersmanagementapi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import net.atos.testoffer.usersmanagementapi.validator.IsAdult;
import net.atos.testoffer.usersmanagementapi.validator.IsResidentOf;

/**
 * DTO to represent a user
 * @author elheni
 *
 */
@Document("users")
public class User {
	/**
	 * Technical Id of a user
	 */
	@Id
    private String id;
	
	/**
	 * The user username
	 */
	@NotBlank(message = "Username is mandatory")
	private String username;
	
	/**
	 * The user First Name
	 */
	private String firstName = "";
	
	/**
	 * The user Last Name
	 */
	private String lastName = "";
	
	/**
	 * The user password
	 */
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	/**
	 * The user date of birth accepted format is ddMMyyyy
	 */
	@NotBlank(message = "Birth Date is mandatory")
	@Pattern(regexp = "^[0-9]{8}$", message = "Birth Date format is ddMMyyyy")
	@IsAdult
	private String birthDate;
	
	/**
	 * The user Email
	 */
	@NotBlank(message = "Email is mandatory")
	@Pattern(regexp = "^.*@.*$", message = "Email is not conform to standard format")
	private String email;
	
	/**
	 * The user Country 2 letters Code 
	 */
	@NotBlank(message = "Country code is mandatory")
	@Pattern(regexp = "^[A-Z]{2}$", message = "Country contains only 2 upper case letters")
	@IsResidentOf
	private String country;
	
	/**
	 * The user phone number 
	 */
	@Pattern(regexp = "[0-9]*", message = "Phone number contains only digits")
	private String phoneNumber = "";
    

    public User(){
    	super();
    }

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
    }
	

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", birthDate=" + birthDate + ", email=" + email + ", country=" + country
				+ ", phoneNumber=" + phoneNumber + "]";
	}
	public static User build() {
		return new User();
	}
	
	public User username(String username) {
		this.setUsername(username);
		return this;
	}
	public User firstName(String firstName) {
		this.setFirstName(firstName);
		return this;
	}
	public User lastName(String lastName) {
		this.setLastName(lastName);
		return this;
	}
	public User password(String password) {
		this.setPassword(password);
		return this;
	}
	public User birthDate(String birthDate) {
		this.setBirthDate(birthDate);
		return this;
	}
	public User email(String email) {
		this.setEmail(email);
		return this;
	}
	public User phoneNumber(String phoneNumber) {
		this.setPhoneNumber(phoneNumber);
		return this;
	}
	
	public User country(String country) {
		this.setCountry(country);
		return this;
	}
}
