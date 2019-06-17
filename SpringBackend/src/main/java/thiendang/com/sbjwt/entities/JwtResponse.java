package thiendang.com.sbjwt.entities;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private List<GrantedAuthority> authorities;
 
	public JwtResponse(String accessToken, String username, List<GrantedAuthority> authorities) {
	    this.token = accessToken;
	    this.username = username;
	    this.authorities = authorities;
	}
 
	public String getAccessToken() {
		return token;
	}
 
	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}
 
	public String getTokenType() {
		return type;
	}
 
	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
 
	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
  
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
}
