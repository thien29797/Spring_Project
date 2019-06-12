package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import thiendang.com.sbjwt.entities.User;


@Service
public class UserService{
	
	public static List<User> listUser = new ArrayList<User>();	
	private static User user;
	
	static {

		try {
		    
			  FileInputStream fis = new FileInputStream("/home/ddthien/userdata.txt");
			  ObjectInputStream ois = new ObjectInputStream(fis);
		    
			  User uArr[] = (User[]) ois.readObject();
			  for (User u : uArr){
			      user = new User(u.getId(), u.getUsername(), u.getPassword());
			      user.setRoles(new String[] { "ROLE_ADMIN" });
			      listUser.add(user);
			      
			  }
		    
		    fis.close();
		    ois.close();
		  } 
		  catch (Exception ex) {
		    System.out.println("Read File Error: " +ex);
		  }		
	}

	public List<User> findAll() {
		return listUser;
	}

	public User findById(int id) {
		for (User user : listUser) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public boolean add(User user) {
		for (User userExist : listUser) {
			if (user.getId() == userExist.getId() || StringUtils.equals(user.getUsername(), userExist.getUsername())) {
				return false;
			}
		}
		listUser.add(user);
		return true;
	}

	public void delete(int id) {
		listUser.removeIf(user -> user.getId() == id);
	}

	public User loadUserByUsername(String username) {
		for (User user : listUser) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public boolean checkLogin(User user) {
		for (User userExist : listUser) {
			if (StringUtils.equals(user.getUsername(), userExist.getUsername())
					&& StringUtils.equals(user.getPassword(), userExist.getPassword())) {
				return true;
			}
		}
		return false;
	}
}
