package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import thiendang.com.sbjwt.entities.Device;


@Service
public class DeviceService{
	
	public static List<Device> listDevice = new ArrayList<Device>();	
	private static Device device;
	
	private static void objectOutputDevice() {
		try {
		    
		   FileOutputStream fos = new FileOutputStream("/home/ddthien/devicedataoff.txt");
		   ObjectOutputStream oos = new ObjectOutputStream(fos);
		    
		   Device d[] = 
				    {new Device("1", "self/diag/devices", "[\n" + 
				    		"“a0e61a30-990b-11e5-8994-feff819cdc9f/”\n" + 
				    		"]", "List the possible device identifiers\n" + 
				    				"NOTE: A dual SDI 2110 is represented as 2 devices"),
				    		new Device("2","self/diag/devices/<device id>",
				    				"{\n" + 
				    				"\"id\":\"a0e61a30-990b-11e5-8994-feff819cdc9f\",\n" + 
				    				"\"node_id\":\"a0e61a30-990b-11e5-8994-feff819cdc9f\",\n" + 
				    				" \"sdi\":{\n" + 
				    				"  \"valid\": \"<0-1>\",\n" + 
				    				"  \"bit_rate\": \"<bitrate>\",\n" + 
				    				"  \"sampling_format\": \"<sampling_format>\",\n" + 
				    				"  \"frame_rate\": \"<rate>\",\n" + 
				    				"  \"video_format\": \"<vid_format>\",\n" + 
				    				"  \"progressive_scan\": \"<0-1>\",\n" + 
				    				"  \"payload_video_id\": \"<32bits>\"\n" + 
				    				"}\n" + 
				    				"}",
				    				"id<R>\n" + 
				    				"Device UUID\n" + 
				    				"node_id<R>\n" + 
				    				"Node UUID\n" + 
				    				"valid<R>\n" + 
				    				"Is flow valid\n" + 
				    				"bit_rate<R>\n" + 
				    				"Video bit rate\n" + 
				    				"  0- HD\n" + 
				    				"  1- SD\n" + 
				    				"  2- 3G")};
		   
		   oos.writeObject(d);
		   System.out.println("Write successfully");
		   
		   fos.close();
		   oos.close();
		 } 
		 catch (IOException ex) {
			 System.out.println("Write file error: " +ex);
		 }
		 
	}	
	
	private static void objectInputDevice() {
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
	
	static {
		objectOutputDevice();
		objectInputDevice();		
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
