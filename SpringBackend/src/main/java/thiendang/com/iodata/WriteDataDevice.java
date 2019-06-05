package thiendang.com.iodata;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import thiendang.com.sbjwt.entities.Device;

public class WriteDataDevice{
	
	private void objectOutputDevice() {
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
	
	public static void main (String[] args) {
		WriteDataDevice dv = new WriteDataDevice();
		dv.objectOutputDevice();
		
	}

}
