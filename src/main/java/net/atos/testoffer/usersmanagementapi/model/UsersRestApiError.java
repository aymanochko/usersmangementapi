package net.atos.testoffer.usersmanagementapi.model;

/**
 * Java DTO used to return error message in the rest response
 * 
 * @author elheni
 *
 */
public class UsersRestApiError {

	/**
	 * the error code
	 */
	private String errorCode;
	
	/**
	 * The error message
	 */
	private String errorMessage;
	public UsersRestApiError() {
		super();
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public static UsersRestApiError build() {
		return new UsersRestApiError();
	}
	
	public UsersRestApiError errorCode(String errorCode) {
		this.setErrorCode(errorCode);
		return this;
	}
	
	public UsersRestApiError errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	@Override
	public String toString() {
		return "UsersRestApiError [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersRestApiError other = (UsersRestApiError) obj;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		return true;
	}
	
	
	
	
}
