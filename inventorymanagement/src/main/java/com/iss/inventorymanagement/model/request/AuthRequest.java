package com.iss.inventorymanagement.model.request;

public class AuthRequest {
	private String username;
	private String password;
	private int isadmin;

	public AuthRequest() {
	}

	public AuthRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	
	public int getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.username+" isAdmin"+this.isadmin;
	}

}
