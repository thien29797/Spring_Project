package thiendang.com.sbjwt.service;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;


import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.entities.DeviceVersionInformation;
import thiendang.com.sbjwt.interfaces.DataProcessingInterface;
import thiendang.com.sbjwt.interfaces.URLDataInterface;
import thiendang.com.sbjwt.views.AttributeViews;

@Service
public class DeviceInformationService implements URLDataInterface, DataProcessingInterface {

	private static List<DeviceInformation> deviceInfoList = new ArrayList<DeviceInformation>();
	private static List<String> deviceIpList = new ArrayList<String>();
	private DeviceInformation deviceInfo;
	private DeviceVersionInformation deviceVers;
	private Object subAttributes;
	private CompletableFuture<Void> future = null;
	private ObjectMapper mapper = new ObjectMapper();
	private long startTime, endTime;

	// Get the version attributes of device
	public Object getVersionAttributes(String ip) {
		DeviceInformation subInfo = (DeviceInformation) processURLData(ip);
		String jsonInString;
		try {
			// To enable pretty print
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			// Map version attributes of DeviceInformation into jsonInString
			jsonInString = mapper
					.writerWithView(AttributeViews.Version_Abtribute.class)
					.writeValueAsString(subInfo);

			subAttributes = jsonInString;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return subAttributes;
	}

	// Get the swshal Type attributes of device
	public Object getSwshalTypeAttributes(String ip) {
		DeviceInformation subInfo = (DeviceInformation) processURLData(ip);
		String jsonInString;
		try {
			// To enable pretty print
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			// Map swshal and type attributes of DeviceInformation into jsonInString
			jsonInString = mapper
					.writerWithView(AttributeViews.Swshal_Type_Abtribute.class)
					.writeValueAsString(subInfo);

			subAttributes = jsonInString;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return subAttributes;
	}

	// Get the asic_slot attributes of device
	public Object getAsicSlotAttributes(String ip) {
		DeviceInformation subInfo = (DeviceInformation) processURLData(ip);
		String jsonInString;
		try {
			// To enable pretty print
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			// Map asic_slot attributes of DeviceInformation into jsonInString
			jsonInString = mapper
					.writerWithView(AttributeViews.AsicSlot_Abtribute.class)
					.writeValueAsString(subInfo);

			subAttributes = jsonInString;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return subAttributes;
	}

	// Get the json data from URL of device and map into the DeviceInformation class
	@Override
	public Object processURLData(String ip) {
		startTime = System.currentTimeMillis();
		try {
			DeviceInformation deviceIn = mapper.readValue(new
					URL("http://" + ip + "/emsfp/node/v1/self/information"), DeviceInformation.class);
			System.out.println();
			System.out.println("getDataURL " + deviceIn);
			deviceInfo = deviceIn;
		} catch (IOException e) {
			deviceInfo = null;
		}
		endTime = System.currentTimeMillis();
		System.out.println();

		System.out.println("Execution time: (ms) " + (endTime - startTime));
		return deviceInfo;
	}

	// Detect the device IP - device information and add into the deviceInfoList
	private void detect_Add_IP(String ip) {
		if (processURLData(ip) != null) {
			System.out.println("checkOurIP " + deviceInfo);
			deviceInfoList.add(deviceInfo);
			deviceIpList.add(ip);
		}
	}

	// Discover device IP and add into the device information list and the device IP list
	public List<DeviceInformation> discoverIP(String ip) throws ExecutionException, InterruptedException {
		System.out.println();

		String mainIP = ip.substring(0, ip.lastIndexOf(".")).trim();
		System.out.println("Main IP: " + mainIP);

		String subIP = ip.substring(ip.lastIndexOf(".") + 1, ip.length()).trim();
		System.out.println("Sub IP: " + subIP);

		int startNumber = Integer.valueOf(subIP.substring(0, subIP.indexOf("-")).trim());
		System.out.printf("\nStart number: %d", startNumber);

		int lastNumber = Integer.valueOf(subIP.substring(subIP.indexOf("-") + 1).trim());
		System.out.printf("\nLast number: %d", lastNumber);

		int sumIPs = lastNumber - startNumber;
		System.out.printf("\nSum IP: %d", +sumIPs);

		for (int i = startNumber; i <= lastNumber; i++) {
			String ipCom = mainIP + "." + i;
			System.out.printf("\nIP %d: %s", i, ipCom);

			future = CompletableFuture.runAsync(() -> {
				detect_Add_IP(ipCom);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException ie) {
					throw new IllegalStateException(ie);
				}
			});
		}
		startTime = System.currentTimeMillis();
		future.get();
		endTime = System.currentTimeMillis();
		System.out.println("Execution future.get() time: (ms) " + (endTime - startTime));
		return deviceInfoList;
	}

	// Get all device IP address
	public List<String> findAllIPs() {
		return deviceIpList;
	}

	// Get all device information
	public List<DeviceInformation> findAllIPsDevice() {
		return deviceInfoList;
	}

	// Refresh device IP list and device information list
	public void refreshList() {
		deviceInfoList = null;
		deviceIpList = null;
	}

	// Get JSON data from device URL then write object data into deviceinformation.txt file
	@Override
	public void writeObjectData(Object deviceInfoObj) {
		try {
			mapper.writeValue(new File("/home/ddthien/deviceinformation.txt"), deviceInfoObj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Read object data from deviceinformation.txt file then map data into DeviceInformation class
	@Override
	public Object readObjectData() {
		try {
			DeviceInformation deviceInfoMapper =
					mapper.readValue(new File("/home/ddthien/deviceinformation.txt"),
							DeviceInformation.class);
			deviceInfo = deviceInfoMapper;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return deviceInfo;
	}
}


