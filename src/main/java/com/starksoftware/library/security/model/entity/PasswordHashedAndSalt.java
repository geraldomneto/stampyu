package com.starksoftware.library.security.model.entity;

public class PasswordHashedAndSalt {

	private String passwordHashed;
	private String salt;

	public PasswordHashedAndSalt(String passwordHashed, String salt) {
		this.passwordHashed = passwordHashed;
		this.salt = salt;
	}

	public PasswordHashedAndSalt() {
		passwordHashed = new String();
		salt = new String();
	}

	public String getPasswordHashed() {
		return passwordHashed;
	}

	public void setPasswordHashed(String passwordHashed) {
		this.passwordHashed = passwordHashed;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
