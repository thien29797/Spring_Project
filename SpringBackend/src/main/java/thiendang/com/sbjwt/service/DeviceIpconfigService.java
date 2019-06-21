package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.entities.DeviceIpconfig;

@Service
public class DeviceIpconfigService {
	
	public static ArrayList<DeviceIpconfig> listIP = new ArrayList<DeviceIpconfig>();
	public DeviceIpconfig deviceIP;
	public DeviceInformation deviceInfo;
	
	public static void writeDeviceIpconfig(Object deviceIP) {
		try {
		    
		   FileOutputStream fos = new FileOutputStream("/home/ddthien/deviceipconfig.txt");
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
	
	public DeviceIpconfig readDeviceIpconfig() {
		try {
		    
			  FileInputStream fis = new FileInputStream("/home/ddthien/deviceipconfig.txt");
			  
			  ObjectInputStream ois = new ObjectInputStream(fis);
		    
			  DeviceIpconfig d =  (DeviceIpconfig) ois.readObject();
			  
			  
	    
		      deviceIP = new DeviceIpconfig(d.getVersion(), d.getLocal_mac(), d.getIp_addr(), d.getSubnet_mask(),
		    		  d.getGateway(), d.getHostname(), d.getPort(), d.getDhcp_enable(), d.getCtl_vlan_id(),
		    		  d.getCtl_vlan_pcp(), d.getCtl_vlan_enable(), d.getData_vlan_id(), d.getCtl_vlan_enable(),
		    		  d.getBootstatus1(), d.getBootstatus2());			      
		      
		      System.out.println("Read successfully");
		    
		    fis.close();
		    ois.close();
		  } 
		  catch (Exception ex) {
		    System.out.println("Read File Error: " +ex);
		  }
		return deviceIP;
	}
	
		
	public DeviceIpconfig checkIP(String ip) {		
		System.out.println();
		
		String mainIP = ip.substring(0, ip.lastIndexOf(".")).trim();
		System.out.println("Main IP: " + mainIP);
		
		String subIP = ip.substring(ip.lastIndexOf(".") + 1, ip.length()).trim();
		System.out.println("Sub IP: " +subIP);
		
		int startNumber = Integer.valueOf(subIP.substring(0, subIP.indexOf("-")).trim());
		System.out.printf("\nStart number: %d", startNumber);
		int lastNumber = Integer.valueOf(subIP.substring(subIP.indexOf("-") +1).trim());
		System.out.printf("\nLast number: %d", lastNumber);
		DeviceIpconfig device = null;
		
		for (int i = startNumber; i <= lastNumber; i++) {
			String ipCom = mainIP + "." + String.valueOf(i);
			System.out.printf("\nIP %d: %s", i, ipCom);
			
			
				if (ipCom.compareTo(deviceIP.getIp_addr()) == 0) {
					
					System.out.println("\nIP: " +deviceIP.getIp_addr());
					device = deviceIP;
					
					
				}
				
				
			
		}
		return device;
		
	}
}
