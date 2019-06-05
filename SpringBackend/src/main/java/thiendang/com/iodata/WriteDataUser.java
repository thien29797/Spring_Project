package thiendang.com.iodata;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import thiendang.com.sbjwt.entities.User;

public class WriteDataUser{
	
	private void objectOutputUser() {
		try {
		    
		   FileOutputStream fos = new FileOutputStream("/home/ddthien/userdata.txt");
		   ObjectOutputStream oos = new ObjectOutputStream(fos);
		    
		   User u[] = { new User  (1, "thien", "123456"),
				   new User  (2, "nhat", "123456")
		   };
		   
		   oos.writeObject(u);
		   System.out.println("Write successfully");
		   
		   fos.close();
		   oos.close();
		 } 
		 catch (IOException ex) {
			 System.out.println("Write file error: " +ex);
		 }
		 
	}	
	

	public static void main (String[] args) {
		WriteDataUser ld = new WriteDataUser();
		ld.objectOutputUser();
	}

}
