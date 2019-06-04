package thiendang.com.iodata;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import thiendang.com.sbjwt.entities.User;

public class LoadData{
	
	private void objectOutputUser() {
		try {
		    
		   FileOutputStream fos = new FileOutputStream("/home/ddthien/userdata2.txt");
		   ObjectOutputStream oos = new ObjectOutputStream(fos);
		    
		   User u = new User(1, "Thien", "123456");
		   
		   oos.writeObject(u);
		   System.out.println("Load successfully");
		   
		   fos.close();
		   oos.close();
		 } 
		 catch (IOException ex) {
			 System.out.println("Write file error: " +ex);
		 }
		 
	}
	
//	private void objectOutputData() {
//		try {
//		    
//		   FileOutputStream fos = new FileOutputStream("/home/ddthien/userdata.txt");
//		   ObjectOutputStream oos = new ObjectOutputStream(fos);
//		    
//		   User u[] = {
//		     new User(1, "Thien", "123456")
//		    ,new User(2, "Nhat", "123456")
//		   	};
//		   
//		   oos.writeObject(u);
//		   
//		   fos.close();
//		   oos.close();
//		 } 
//		 catch (IOException ex) {
//			 System.out.println("Loi ghi file: " +ex);
//		 }
//		 
//	}
	public static void main (String[] args) {
		LoadData ld = new LoadData();
		ld.objectOutputUser();
	}

}
