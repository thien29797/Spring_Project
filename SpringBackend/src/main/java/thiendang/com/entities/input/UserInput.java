package thiendang.com.entities.input;

import java.util.Arrays;

public class UserInput {
	private String username;
	private String password;
	private String[] roles;

	public UserInput() {
		username = "";
		password = "";
		roles = new String[] {};
	}
	
	@Override
	public String toString() {
		return "UserInput [username=" + username + ", password=" + password + ", roles="
				+ Arrays.toString(roles) + "]";
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

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
}
