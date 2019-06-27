package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.interfaces.DataProcessingInterface;
import thiendang.com.sbjwt.interfaces.URLDataInterface;

@Service
public class DeviceInformationService implements URLDataInterface, DataProcessingInterface {

	private static List<DeviceInformation> deviceInfoList = new ArrayList<DeviceInformation>();
	private static List<String> deviceIpList = new ArrayList<String>();
	private DeviceInformation deviceInfo;
	private ObjectMapper mapper = new ObjectMapper();


	@Override
	public Object getDataURL(String ip) {

		try {
			DeviceInformation deviceIn = mapper.readValue(new
					URL("http://" + ip + "/emsfp/node/v1/self/information"), DeviceInformation.class);
			System.out.println();
			System.out.println(deviceIn);
			deviceInfo = deviceIn;
		}
		catch (IOException e) {
			deviceInfo = null;
		}
		return deviceInfo;
	}
			
	public List<DeviceInformation> checkIP(String ip) {
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
//			try {
//				device = (DeviceInformation) getDataURL(ipCom);
//				deviceInfoList.add(device);
//			} catch (IOException e) {
//				continue;
//			}
			if (getDataURL(ipCom) != null) {
				deviceInfoList.add(deviceInfo);
				deviceIpList.add(ipCom);
			}
		}
		return deviceInfoList;
	}

	public List<String> findAllIPs() {
		return deviceIpList;
	}

	public List<DeviceInformation> findAllIPsDevice() {
			return deviceInfoList;
	}

	@Override
	public void writeObjectData(String ip) {
		try {

			FileOutputStream fos = new FileOutputStream("/home/ddthien/deviceinformation.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(getDataURL(ip));

			System.out.println("Write successfully");

			fos.close();
			oos.close();
		}
		catch (IOException ex) {
			System.out.println("Write file error: " +ex);
		}
	}

	@Override
	public Object readObjectData() {
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
}
