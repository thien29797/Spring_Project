package thiendang.com.sbjwt.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;


import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.interfaces.DataProcessingInterface;
import thiendang.com.sbjwt.interfaces.URLDataInterface;
import thiendang.com.sbjwt.views.AbtributesView;

@Service
public class DeviceInformationService implements URLDataInterface, DataProcessingInterface{

	private static List<DeviceInformation> deviceInfoList = new ArrayList<DeviceInformation>();
	private static List<String> deviceIpList = new ArrayList<String>();
	private DeviceInformation deviceInfo;
	private Object subAbtributes;
	private static ObjectMapper mapper = new ObjectMapper();

	public Object getOptionalAbtributes(String ip) {
		//mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
		DeviceInformation subInfo = (DeviceInformation) getDataURL(ip);
		try {
			Object jsonInString =
					mapper.writerWithView(AbtributesView.Version_Abtribute.class).writeValueAsString(subInfo);
			subAbtributes = jsonInString;
			System.out.println(subAbtributes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return subAbtributes;
	}

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

	public DeviceInformation getDataURL1(String ip) {

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

	private void checkOwnIP(String ip) {
		if (getDataURL(ip) != null) {
			deviceInfoList.add(deviceInfo);
			deviceIpList.add(ip);
		}
		else {
			deviceInfoList = null;
			deviceIpList = null;
		}
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
		int sumIPs = lastNumber - startNumber;
		System.out.printf("\nSum IP: %d",	 +sumIPs);
		CountDownLatch latch = new CountDownLatch(sumIPs);

		for (int i = startNumber; i <= lastNumber; i++) {
			String ipCom = mainIP + "." + String.valueOf(i);
			System.out.printf("\nIP %d: %s", i, ipCom);

			try {
				CompletableFuture.supplyAsync(getDataURL1(ipCom)).thenAccept(d1 -> {
					printData((String) d1);
					latch.countDown();
				});
				latch.await();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return deviceInfoList;
	}

	private static void printData(String data){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Synchronously printing "+data);
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
