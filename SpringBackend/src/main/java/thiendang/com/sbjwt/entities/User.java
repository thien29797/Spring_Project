package thiendang.com.sbjwt.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"authorities" })

public class User implements Serializable{

	public static int idInit = 0;
	private int id;
	private String username;
	private String password;
	private String[] roles;

	public User() {
		id = ++idInit;
		username = "";
		password = "";
		roles = new String[] {};
	}
	
	public User(String username, String password) {
		id = ++idInit;
		this.username = username;
		this.password = password;
		roles = new String[] {};
	}
	
	public User(int id, String username, String password, String[] roles) {
		this.id= id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", roles="
				+ Arrays.toString(roles) + "]";
	}
	
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
