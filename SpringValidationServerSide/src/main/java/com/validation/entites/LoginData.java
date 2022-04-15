package com.validation.entites;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginData {

	@NotBlank(message="User Name Can not be empty")
	@Size(min=3, max=12, message="User name must be between 3-12 characters !!")
	private String userName;
	
	
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Invalid email !!")
	//@Email(message="Invalid email !!")
	private String email;
	
	@AssertTrue()
	private boolean agreed;
	
	

	public Boolean getAgreed() {
		return agreed;
	}

	public void setAgreed(Boolean agreed) {
		this.agreed = agreed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "LoginData [userName=" + userName + ", email=" + email + "]";
	}

	
	


	
	
}
