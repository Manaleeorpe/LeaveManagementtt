package com.example.demo.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginRequest {
	@NotNull(message = "Email is mandatory")
    @Email(message = "Require email format")
    private String email;

    @NotNull(message = "Password is mandatory")
    private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    // Getters and setters
}
