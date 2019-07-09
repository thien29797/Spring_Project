package thiendang.com.sbjwt.service.user;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import thiendang.com.entities.input.LogInInput;
import thiendang.com.entities.input.UserInput;
import thiendang.com.sbjwt.entities.User;

@Service
public class UserService {
	
	public static List<User> listUser = new ArrayList<User>();	
	
	//Encode user info
	public static String encodeString(String text) throws UnsupportedEncodingException{
		byte[] bytes = text.getBytes();
		String encodeString = Base64.encode(bytes);
		return encodeString;
	}
		
	//Decode user info
	public static String decodeString(String encodeString) throws UnsupportedEncodingException {
		byte[] decodeBytes = Base64.decode(encodeString);
		String str = new String(decodeBytes);
		return str;
	}

	// Write User object into local storing
	private static void objectOutputUser() {
		try {

		   FileOutputStream fos = new FileOutputStream("/home/ddthien/userdata.txt");
		   ObjectOutputStream oos = new ObjectOutputStream(fos);
		   User u[] = { new User(encodeString("nhat"),
				   encodeString("123456")), new User(encodeString("thien"),
				   encodeString("123456")) };
		   oos.writeObject(u);
		   //System.out.println("Write successfully");

		   fos.close();
		   oos.close();
		 }
		 catch (IOException ex) {
			 System.out.println("Write file error: " +ex);
		 }
			 
	}

	// Read user object from local storing
	private static void objectInputUser() {
		try {
			  FileInputStream fis = new FileInputStream("/home/ddthien/userdata.txt");
			  ObjectInputStream ois = new ObjectInputStream(fis);

			  User uArr[] = (User[]) ois.readObject();
			  for (User u : uArr){
			      User user = new User(u.getId(), u.getUsername(),
			    		  u.getPassword(), new String[] { "ROLE_ADMIN" });

			      //System.out.println(user);
			      listUser.add(user);
			  }
			    
		    fis.close();
		    ois.close();
		  }
		  catch (Exception ex) {
		    System.out.println("Read File Error: " +ex);
		  }
	}

	static {
		objectOutputUser();
		objectInputUser();
	}

	// Get all user information
	public List<User> findAll() {
		return listUser;
	}

	// Get user by id
	public User findById(int id) {
		for (User user : listUser) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	// Add user into file storing
	public boolean add(UserInput userInput) {
		for (User user : listUser) {
			if (StringUtils.equals(userInput.getUsername(), user.getUsername())) {
				return false;
			}
		}
		User user = new User();
		
		user.setUsername(userInput.getUsername());
		user.setPassword(userInput.getPassword());
		user.setRoles(userInput.getRoles());
		listUser.add(user);
		System.out.println("save user " + user.toString());
		return true;
	}

	// Delete user in file storing
	public void delete(int id) {
		listUser.removeIf(user -> user.getId() == id);
	}

	// Get user by username
	public User loadUserByUsername(String username) {
		for (User user : listUser) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	// Check user login
	public boolean checkLogin(LogInInput logInInput) throws UnsupportedEncodingException {
		for (User user : listUser) {
			System.out.println("user " + user.toString());
			if (StringUtils.equals(logInInput.getUsername(), user.getUsername())
					&& StringUtils.equals(logInInput.getPassword(), user.getPassword())) {
				return true;
			}
		}
		return false;
	}

}
