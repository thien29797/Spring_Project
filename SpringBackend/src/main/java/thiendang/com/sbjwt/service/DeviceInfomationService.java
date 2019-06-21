package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.entities.DeviceIpconfig;

public class DeviceInfomationService {
	
		
		public DeviceInformation deviceInfo;
		
		public static void writeDeviceInfomation(Object deviceIP) {
			try {
			    
			   FileOutputStream fos = new FileOutputStream("/home/ddthien/deviceinformation.txt");
			   ObjectOutputStream oos = new ObjectOutputStream(fos);
			    
			   oos.writeObject(deviceIP);
			   System.out.println("Write successfully");
			   
			   fos.close();
			   oos.close();
			 } 
			 catch (IOException ex) {
				 System.out.println("Write file error: " +ex);
			 }
			 
		}	
		
		public DeviceInformation readDeviceInformation() {
			try {
			    
				  FileInputStream fis = new FileInputStream("/home/ddthien/deviceinformation.txt");
				  
				  ObjectInputStream ois = new ObjectInputStream(fis);
			    
				  DeviceInformation d =  (DeviceInformation) ois.readObject();
				  
				  
		    
			      deviceInfo = new DeviceInformation(d.getCurrent_version(), d.getEmsfp_version(),
			    		  d.getAsic_version(), d.getSw_sha1(), d.getType(), d.getAsic_slot_00(),
			    		  d.getAsic_slot_01(), d.getAsic_slot_02(), d.getAsic_slot_03(), d.getHw_version());			      
			      
			      System.out.println("Read successfully");
			    
			    fis.close();
			    ois.close();
			  } 
			  catch (Exception ex) {
			    System.out.println("Read File Error: " +ex);
			  }
			return deviceInfo;
		}
		
			
		public DeviceInformation checkIP(String ip) {		
			System.out.println();
			
			String mainIP = ip.substring(0, ip.lastIndexOf(".")).trim();
			System.out.println("Main IP: " + mainIP);
			
			String subIP = ip.substring(ip.lastIndexOf(".") + 1, ip.length()).trim();
			System.out.println("Sub IP: " +subIP);
			
			int startNumber = Integer.valueOf(subIP.substring(0, subIP.indexOf("-")).trim());
			System.out.printf("\nStart number: %d", startNumber);
			int lastNumber = Integer.valueOf(subIP.substring(subIP.indexOf("-") +1).trim());
			System.out.printf("\nLast number: %d", lastNumber);
			DeviceInformation device = null;
			
			for (int i = startNumber; i <= lastNumber; i++) {
				String ipCom = mainIP + "." + String.valueOf(i);
				System.out.printf("\nIP %d: %s", i, ipCom);
				
//				
//					if (ipCom.compareTo(deviceInfo.getIp_addr()) == 0) {
//						
//						System.out.println("\nIP: " +deviceInfo.getIp_addr());
//						device = deviceInfo;
//						
//						
//					}
					
					
				
			}
			return device;
			
		}	
}
