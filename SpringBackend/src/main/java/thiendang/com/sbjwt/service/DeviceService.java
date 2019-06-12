package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	
	public Device findDeviceById(String id) {
		for (Device device : listDevice) {
			if (device.getId().compareTo(id) == 0) {
				return device;
			}
		}
		return null;
	}
	
	public boolean addDevice(Device device) {
		for (Device deviceExist : listDevice) {
			if (device.getId() == deviceExist.getId() || StringUtils.equals(device.getId(), device.getResource())) {
				return false;
			}
		}
		listDevice.add(device);
		return true;
	}
	
	public void deleteDevice(String id) {
		listDevice.removeIf(user -> device.getId() == id);
	}

}
