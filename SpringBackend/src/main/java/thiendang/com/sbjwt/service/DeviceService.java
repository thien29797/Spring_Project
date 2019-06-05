package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import thiendang.com.sbjwt.entities.Device;


@Service
public class DeviceService{
	
	public static List<Device> listDevice = new ArrayList<Device>();	
	private static Device device;
	
	static {

		try {
		    
			  FileInputStream fis = new FileInputStream("/home/ddthien/devicedataoff.txt");
			  
			  ObjectInputStream ois = new ObjectInputStream(fis);
		    
			  Device dArr[] =  (Device[]) ois.readObject();
			  
			  for (Device d : dArr) {
//			  System.out.println(d.toString());
//		      System.out.println("id: " + d.getId());
//		      System.out.println("resource: " + d.getResource());
//		      System.out.println("content: " + d.getContent());
//		      System.out.println("descrpition: " + d.getDescription());
	    
		      device = new Device(d.getId(), d.getResource(), d.getContent(), d.getDescription());			      
		      listDevice.add(device);
			}
		    
		    fis.close();
		    ois.close();
		  } 
		  catch (Exception ex) {
		    System.out.println("Read File Error: " +ex);
		  }		
	}

	public List<Device> findAllDevices() {
		return listDevice;
	}

}
