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
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;


import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.interfaces.DataProcessingInterface;
import thiendang.com.sbjwt.interfaces.URLDataInterface;
import thiendang.com.sbjwt.views.AttributeViews;

@Service
public class DeviceInformationService implements URLDataInterface, DataProcessingInterface{

	private static List<DeviceInformation> deviceInfoList = new ArrayList<DeviceInformation>();
	private static List<String> deviceIpList = new ArrayList<String>();
	private DeviceInformation deviceInfo;
	private Object subAttributes;
	private CompletableFuture<Void> future = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private long startTime, endTime;

	// Get version attributes of device
	public Object getVersionAttributes(String ip) {
		DeviceInformation subInfo = (DeviceInformation) getDataURL(ip);
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

	// Get swshal Type attribute of device
	public Object getSwshalTypeAttributes(String ip) {
		DeviceInformation subInfo = (DeviceInformation) getDataURL(ip);
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

	// Get asic_slot attribute of device
	public Object getAsicSlotAttributes(String ip) {
		DeviceInformation subInfo = (DeviceInformation) getDataURL(ip);
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

	// Get json from url of device and map into DeviceInformation class
	@Override
	public Object getDataURL(String ip) {
		startTime = System.currentTimeMillis();
		try {
			DeviceInformation deviceIn = mapper.readValue(new
					URL("http://" + ip + "/emsfp/node/v1/self/information"), DeviceInformation.class);
			System.out.println();
			System.out.println("getDataURL " + deviceIn);
			deviceInfo = deviceIn;
		}
		catch (IOException e) {
			deviceInfo = null;
		}
		endTime = System.currentTimeMillis();
		System.out.println();
		System.out.println("Execution time: (ms) " + (endTime-startTime));
		return deviceInfo;
	}

	// Detect the device IP - device information and add into the list
	private void detect_Add_IP(String ip) {
		if (getDataURL(ip) != null) {
			System.out.println("checkOurIP " + deviceInfo);
			deviceInfoList.add(deviceInfo);
			deviceIpList.add(ip);
		}
	}

    // Discover our device IP and return the device information list and the device IP list
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
		future.get();

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
