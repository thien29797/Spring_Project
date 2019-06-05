package thiendang.com.iodata;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import thiendang.com.sbjwt.entities.Device;

public class WriteDataDevice{
	
	private void objectOutputDevice() {
		try {
		    
		   FileOutputStream fos = new FileOutputStream("/home/ddthien/devicedataoff.txt");
		   ObjectOutputStream oos = new ObjectOutputStream(fos);
		    
		   Device d[] = {
				    new Device("1", "self/diag/flow/<flow id>", "{\n" + 
				    		"  \"api_info\": {\n" + 
				    		"       \"api_version_number\": \"1\"\n" + 
				    		"}", "api_version_number<R>\n" + 
				    				"RestAPI version")
				   	};
		   
		   oos.writeObject(d);
		   System.out.println("Write successfully");
		   
		   fos.close();
		   oos.close();
		 } 
		 catch (IOException ex) {
			 System.out.println("Write file error: " +ex);
		 }
		 
	}
	
	private void objectInputDevice() {
		  try {
		    
			  FileInputStream fis = new FileInputStream("/home/ddthien/devicedataoff.txt");
			  ObjectInputStream ois = new ObjectInputStream(fis);
		    
			  Device dArr[] = (Device[]) ois.readObject();
			  for (Device d : dArr){
			      System.out.println(d.toString());
			      System.out.println("id: " + d.getId());
			      System.out.println("resource: " + d.getResource());
			      System.out.println("content: " + d.getContent());
			      System.out.println("descrpition: " + d.getDescription());
		    }
		    
		    fis.close();
		    ois.close();
		  } 
		  catch (Exception ex) {
		    System.out.println("Read File Error: "+ex);
		  }
		 
	}

	public static void main (String[] args) {
		WriteDataDevice dv = new WriteDataDevice();
		dv.objectOutputDevice();
		dv.objectInputDevice();
	}

}
