package com.iss.inventorymanagement.model.request;

import javax.validation.constraints.NotNull;

public class ProfileRequestModel {
	@NotNull(message = "Name cannot be null")
	private String name;
	@NotNull(message = "UserName cannot be null")
	private String username;
	@NotNull(message = "Password cannot be null")
	private String password;
	@NotNull(message = "Select admin or not")
	private int isAdmin;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

}
