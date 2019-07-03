package thiendang.com.sbjwt.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import thiendang.com.entities.input.LogInInput;
import thiendang.com.entities.input.UserInput;
import thiendang.com.sbjwt.entities.DeviceInformation;
import thiendang.com.sbjwt.entities.DeviceIpconfig;
import thiendang.com.sbjwt.entities.JwtResponse;
import thiendang.com.sbjwt.entities.ResponseMessage;
import thiendang.com.sbjwt.entities.User;
import thiendang.com.sbjwt.service.DeviceInformationService;
import thiendang.com.sbjwt.service.DeviceIpconfigService;
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
	private DeviceInformationService deviceInformationService;
	
	@Autowired
	private DeviceIpconfigService deviceIpconfigService;

	/* ---------------- GET ALL USER ------------------------ */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userService.findAll();
		System.out.println("list users " + users.toString());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/* ---------------- GET USER BY ID ------------------------ */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		User user = userService.findById(id);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>("Not Found User", HttpStatus.NO_CONTENT);
	}

	/* ---------------- CREATE NEW USER ------------------------ */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Valid @RequestBody UserInput signUpInput) {
		System.out.println("user " + signUpInput.toString());
		if (userService.add(signUpInput)) {
			return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken"), HttpStatus.BAD_REQUEST);
		}
	}

	/* ---------------- DELETE USER ------------------------ */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable int id) {
		userService.delete(id);
		return new ResponseEntity<>("Deleted!", HttpStatus.OK);
	}

	/*---------------------LOGIN-----------------------------*/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody LogInInput credentials) throws UnsupportedEncodingException {
		String result = "";
		
		System.out.print("logininput " + credentials);
		if (userService.checkLogin(credentials)) {
			User user = userService.loadUserByUsername
				(credentials.getUsername());
			
			result = jwtService.generateTokenLogin(user.getUsername());
			System.out.print("token " + result);
			return ResponseEntity.ok(new JwtResponse(result, user.getId(),
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

	/*GET DEVICE IPCONFIG FROM STORE'S COMPUTER*/
	@RequestMapping(value = "devices/ipconfig", method = RequestMethod.GET)
	public ResponseEntity<?> getIpconfigData() {
		DeviceIpconfig deviceIP = (DeviceIpconfig) deviceIpconfigService.readObjectData();
		if (deviceIP != null)
			return new ResponseEntity<>(deviceIP, HttpStatus.OK);
		else
			return new ResponseEntity<>("IPCONFIG DATA ARE EMPTY", HttpStatus.NOT_FOUND);
	}

	/*GET DEVICE INFORMATION FROM STORE'S COMPUTER*/
	@RequestMapping(value = "devices/information", method = RequestMethod.GET)
	public ResponseEntity<?> getInformationData() {
		DeviceInformation deviceInfo = (DeviceInformation) deviceInformationService.readObjectData();
		if (deviceInfo != null)
			return new ResponseEntity<>(deviceInfo, HttpStatus.OK);
		else
			return new ResponseEntity<>("INFORMATION DATA ARE EMPTY", HttpStatus.NOT_FOUND);
	}

	/*-------------GET AND WRITE DEVICE INFORMATION----------------------*/
	@RequestMapping(value = "devices/{ip}/information", method = RequestMethod.GET)
	public ResponseEntity<?> getDevicesInformation(@PathVariable String ip) throws IOException {
		DeviceInformation deviceInfo = (DeviceInformation) deviceInformationService.getDataURL(ip);
		if (deviceInfo != null) {
			deviceInformationService.writeObjectData(ip);
			return new ResponseEntity<>(deviceInfo, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("NOT FOUND INFORMATION DEVICE", HttpStatus.NOT_FOUND);
		}
	}
	
	/*---------------GET AND WRITE DEVICE IPCONFIG-------------------------*/
	@RequestMapping(value = "devices/{ip}/ipconfig", method = RequestMethod.GET)
	public ResponseEntity<?> getDevicesIpconfig(@PathVariable String ip) {
		DeviceIpconfig deviceIp = (DeviceIpconfig) deviceIpconfigService.getDataURL(ip);
		if (deviceIp != null) {
			deviceIpconfigService.writeObjectData(ip);
			return new ResponseEntity<>(deviceIp, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("NOT FOUND IPCONFIG DEVICE", HttpStatus.NOT_FOUND);
		}
	}
	
	/*------------------CHECK IP WITH RANGE_EX: devices/check-ip/10.220.20.200-255------------*/
	@RequestMapping(value = "devices/check-ip/{ip}", method = RequestMethod.GET)
	public ResponseEntity<?> checkIPs(@PathVariable String ip) throws ExecutionException, InterruptedException {

		List<DeviceInformation> list = deviceInformationService.discoverIP(ip);
		if (list.isEmpty() == false) {
			return new ResponseEntity<>(deviceInformationService.findAllIPsDevice(),
                    HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("NOT FOUND ANY INFORMATION DEVICE WITH THIS RANGE IP",
                    HttpStatus.NOT_FOUND);
		}
	}

	/*GET ALL IP ADDRESS DEVICES*/
	@RequestMapping(value = "/devices/ips", method = RequestMethod.GET)
	public ResponseEntity<?> getIPsList() {
		if (deviceInformationService.findAllIPs().isEmpty() == false)
			return new ResponseEntity<>(deviceInformationService.findAllIPs(), HttpStatus.OK);
		else
			return new ResponseEntity<>("DEVICE LIST IS EMPTY", HttpStatus.NOT_FOUND);
	}

	/*GET VERSION FIELDS FROM DEVICE*/
	@RequestMapping(value = "/devices/{ip}/version", method = RequestMethod.GET)
	public ResponseEntity<?> getVersionFields(@PathVariable String ip) {
		Object subDeviceFields = deviceInformationService.getVersionAttributes(ip);
		if (subDeviceFields != null) {
			return new ResponseEntity<>(subDeviceFields, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("NOT FOUND DATA", HttpStatus.NOT_FOUND);
		}
	}

	/*GET SWSHAL AND TYPE FIELDS FROM DEVICE*/
	@RequestMapping(value = "/devices/{ip}/sw-type", method = RequestMethod.GET)
	public ResponseEntity<?> getSwTypeFields(@PathVariable String ip) {
		Object subDeviceFields = deviceInformationService.getSwshalTypeAttributes(ip);
		if (subDeviceFields != null) {
			return new ResponseEntity<>(subDeviceFields, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("NOT FOUND DATA", HttpStatus.NOT_FOUND);
		}
	}

	/*GET ASIC_SLOT FIELDS FROM DEVICE*/
	@RequestMapping(value = "/devices/{ip}/asicslot", method = RequestMethod.GET)
	public ResponseEntity<?> getAsicSlotFields(@PathVariable String ip) {
		Object subDeviceFields = deviceInformationService.getAsicSlotAttributes(ip);
		if (subDeviceFields != null) {
			return new ResponseEntity<>(subDeviceFields, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("NOT FOUND DATA", HttpStatus.NOT_FOUND);
		}
	}
}
