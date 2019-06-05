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
		    
		   FileOutputStream fos = new FileOutputStream("/home/ddthien/devicedata.txt");
		   ObjectOutputStream oos = new ObjectOutputStream(fos);
		    
		   Device d[] = {
				     new Device("1", "self/diag/flow", "[a04f66a2-9910-11e5-8894-feff819cdc9f/]",
				    		 "List the possible flow identifiers")
				    ,new Device("2", "self/diag/flow/<flow id>", "{\n" + 
				    		"  \"api_info\": {\n" + 
				    		"       \"api_version_number\": \"1\"\n" + 
				    		"   },\n" + 
				    		"   \"format\":{\n" + 
				    		"        \"type\":\"audio\",\n" + 
				    		"       \"decoded_ptime\":\"<0-4>\"\n" + 
				    		"   },\n" + 
				    		"   \"igmp\":{\n" + 
				    		"     \"query_count\":\"<32 bits>\",\n" + 
				    		"     \"join_count\":\"<32 bits>\",\n" + 
				    		"     \"join_received\":\"<32 bits>\",\n" + 
				    		"     \"version\":\"<8 bits>\",\n" + 
				    		"     \"querrier_address\":\"<192.168.0.0>\",\n" + 
				    		"     \"keep_alive_count\":\"N/A\",\n" + 
				    		"     \"enable\":\"<0-1>\",\n" + 
				    		"     \"force_join\":\"<0-1>\"\n" + 
				    		"   },\n" + 
				    		"   \"2110\":{\n" + 
				    		"     \"enable_flow\":\"<0-1>\"\n" + 
				    		"   }\n" + 
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
		    
			  FileInputStream fis = new FileInputStream("/home/ddthien/devicedata.txt");
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
