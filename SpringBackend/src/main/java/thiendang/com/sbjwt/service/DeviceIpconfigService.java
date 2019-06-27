package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.entities.DeviceIpconfig;
import thiendang.com.sbjwt.interfaces.URLDataInterface;

@Service
public class DeviceIpconfigService implements URLDataInterface{
	
	private static ArrayList<DeviceIpconfig> listIP = new ArrayList<DeviceIpconfig>();
	private DeviceIpconfig deviceIP;
	private ObjectMapper mapper = new ObjectMapper();

	public void writeDeviceIpconfig(Object deviceIP) {
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

	@Override
	public Object getDataURL(String ip) {

		try {
			DeviceIpconfig deviceIp = mapper.readValue(new
					URL("http://" + ip + "/emsfp/node/v1/self/ipconfig"), DeviceIpconfig.class);
			System.out.println();
			System.out.println(deviceIp);
			deviceIP = deviceIp;
		}
		catch (IOException e) {
			deviceIP = null;
		}
		return deviceIP;
	}

}
