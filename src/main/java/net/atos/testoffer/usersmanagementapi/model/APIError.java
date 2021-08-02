package net.atos.testoffer.usersmanagementapi.model;

/**
 * Enumeration that list all business errors returned by the Rest Service
 * 
 * @author A622187
 *
 */
public enum APIError {
	UMERR0001("No user found with the given username"), 
	UMERR0002("No user found with the given id"),
	UMERR0003("A user already exists with the same username");
	
	private String errorDescription;
	APIError(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public String getErrorDescription() {
		return this.errorDescription;
	}	
	
}
