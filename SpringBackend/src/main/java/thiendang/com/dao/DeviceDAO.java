package thiendang.com.dao;

import java.awt.List;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import thiendang.com.sbjwt.entities.Device;

@Repository
public class DeviceDAO {
	private static final Map<String, Device> deviceMap = new HashMap<String, Device>();
	 
    static {
        initDevice();
    }
    
    private static void initDevice() {
    	try {
		    
			  FileInputStream fis = new FileInputStream("/home/ddthien/devicedata.txt");
			  ObjectInputStream ois = new ObjectInputStream(fis);
		    
			  Device dArr[] = (Device[]) ois.readObject();
			  for (Device d : dArr) {
			      System.out.println(d.toString());
			      System.out.println("id: " + d.getId());
			      System.out.println("resource: " + d.getResource());
			      System.out.println("content: " + d.getContent());
			      System.out.println("descrpition: " + d.getDescription());
			      deviceMap.put(d.getId(), d);
		    }
		    
		    fis.close();
		    ois.close();
		  } 
		  catch (Exception ex) {
		    System.out.println("Read File Error: "+ex);
		  }
    }
    
    public Device getDevice(String divNo) {
        return deviceMap.get(divNo);
    }
 
    public Device addEmployee(Device div) {
        deviceMap.put(div.getId(), div);
        return div;
    }
 
    public Device updateEmployee(Device div) {
        deviceMap.put(div.getId(), div);
        return div;
    }
 
    public void deleteEmployee(String div) {
        deviceMap.remove(div);
    }
 
    public ArrayList<Device> getAllEmployees() {
        Collection<Device> c = deviceMap.values();
        ArrayList<Device> list = new ArrayList<Device>();
        list.addAll(c);
        return list;
    }
}
