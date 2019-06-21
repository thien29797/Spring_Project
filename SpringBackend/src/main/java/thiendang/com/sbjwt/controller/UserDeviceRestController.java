package thiendang.com.sbjwt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.entities.DeviceIpconfig;
import thiendang.com.sbjwt.entities.JwtResponse;
import thiendang.com.sbjwt.entities.User;
import thiendang.com.sbjwt.service.DeviceIpconfigService;
import thiendang.com.sbjwt.service.DeviceService;
import thiendang.com.sbjwt.service.JwtService;
import thiendang.com.sbjwt.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class UserDeviceRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private DeviceIpconfigService deviceIpconfigService;

	/* ---------------- GET ALL USER ------------------------ */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser() {
		return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	}

	/* ---------------- GET USER BY ID ------------------------ */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		User user = userService.findById(id);
		if (user != null) {
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
	}

	/* ---------------- CREATE NEW USER ------------------------ */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody User user) {
		if (userService.add(user)) {
			return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("User Existed!", HttpStatus.BAD_REQUEST);
		}
	}

	/* ---------------- DELETE USER ------------------------ */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable int id) {
		userService.delete(id);
		return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
	}

	/*---------------------LOGIN-----------------------------*/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody User credentials) {
		String result = "";
		
		System.out.print("user " + credentials);
		if (userService.checkLogin(credentials)) {
			User user = userService.loadUserByUsername
				(credentials.getUsername());
			
			result = jwtService.generateTokenLogin(user.getUsername());
			System.out.print("token " + result);
			return ResponseEntity.ok(new JwtResponse(result,
				user.getUsername(), user.getAuthorities()));
		}
		return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body("Wrong userId and password");
	}
	
	/*-----------------------LOGOUT----------------------------*/
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    org.springframework.security.core.Authentication auth 
	    	= SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	
//	/*-----------------GET ALL DEVICES----------------------*/
//	@RequestMapping(value = "/devices", method = RequestMethod.GET)
//	public ResponseEntity<List<Device>> getAllDevices() {
//		return new ResponseEntity<List<Device>>(deviceService.findAllDevices(), HttpStatus.OK);
//	}
//	
//	/*-----------------GET DEVICE BY ID---------------------*/
//	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
//	public ResponseEntity<Object> getDeviceById(@PathVariable String id) {
//		Device device = deviceService.findDeviceById(id);
//		if (device != null) {
//			return new ResponseEntity<Object>(device, HttpStatus.OK);
//		}
//		return new ResponseEntity<Object>("Not Found Device", HttpStatus.NO_CONTENT);
//	}
//	
//	/*------------------CREATE NEW DEVICE---------------------*/
//	@RequestMapping(value = "/devices", method = RequestMethod.POST)
//	public ResponseEntity<String> createDevice(@RequestBody Device device) {
//		if (deviceService.addDevice(device)) {
//			return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
//		} else {
//			return new ResponseEntity<String>("Device Existed!", HttpStatus.BAD_REQUEST);
//		}
//	}
//	
//	/* ---------------- DELETE DEVICE ------------------------ */
//	@RequestMapping(value = "/devices/{id}", method = RequestMethod.DELETE)
//	public ResponseEntity<String> deleteDeviceById(@PathVariable String id) {
//		deviceService.deleteDevice(id);
//		return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
//	}
	
	/*-------------GET DEVICE INFOMATION----------------------*/
	@GetMapping(value = "devices/information")
	@ResponseBody
	public ResponseEntity<Object> getDevicesInformation() {
		ObjectMapper mapper = new ObjectMapper();
        try {
            DeviceInformation deviceInfo = mapper.readValue(new 
            		URL("http://10.220.20.205/emsfp/node/v1/self/information"), DeviceInformation.class);
            System.out.println(deviceInfo);
            return new ResponseEntity<Object>(deviceInfo, HttpStatus.OK);
        } catch (IOException e) {            
            e.printStackTrace();
            return new ResponseEntity<Object>("NOT FOUND INFORMATION DEVICE", HttpStatus.BAD_REQUEST);
        }       
	}
	
	/*------------GET DEVICE IPCONFIG-------------------------*/
	@GetMapping(value = "devices/ipconfig")
	@ResponseBody
	public ResponseEntity<Object> getDevicesIpconfig() {
		ObjectMapper mapper1 = new ObjectMapper();
        try {
            DeviceIpconfig deviceIp = mapper1.readValue(new 
            		URL("http://10.220.20.205/emsfp/node/v1/self/ipconfig"), DeviceIpconfig.class);
            System.out.println(deviceIp);
            deviceIpconfigService.writeDeviceIpconfig(deviceIp);
            return new ResponseEntity<Object>(deviceIp, HttpStatus.OK);
        } catch (IOException e) {            
            e.printStackTrace();
            return new ResponseEntity<Object>("NOT FOUND IPCONFIG DEVICE", HttpStatus.BAD_REQUEST);
        }
	}
	
	/*---------------------CHECK IP-----------------------------*/
	@RequestMapping(value = "/check-ip/{ip}", method = RequestMethod.GET)
	public ResponseEntity<Object> checkIPs(@PathVariable String ip) {
		ObjectMapper mapper1 = new ObjectMapper();
        try {
            DeviceIpconfig deviceIp = mapper1.readValue(new 
            		URL("http://10.220.20.205/emsfp/node/v1/self/ifconfig"), DeviceIpconfig.class);
            System.out.println(deviceIp);
            deviceIpconfigService.writeDeviceIpconfig(deviceIp);
            
        } catch (IOException e) {            
            e.printStackTrace();
            
        }
		deviceIpconfigService.readDeviceIpconfig();
		
		if (deviceIpconfigService.checkIP(ip) != null) {			
			return new ResponseEntity<Object>(deviceIpconfigService.checkIP(ip), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<Object>("NOT FOUND DEVICE", HttpStatus.NOT_FOUND);
		}
	}

}
