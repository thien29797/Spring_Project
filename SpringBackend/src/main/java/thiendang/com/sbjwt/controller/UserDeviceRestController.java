package thiendang.com.sbjwt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import thiendang.com.sbjwt.entities.Device;
import thiendang.com.sbjwt.entities.User;
import thiendang.com.sbjwt.service.DeviceService;
import thiendang.com.sbjwt.service.JwtService;
import thiendang.com.sbjwt.service.UserService;


@RestController
@RequestMapping("/rest")
public class UserDeviceRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;
	
	@Autowired
    private DeviceService deviceService;

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
	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody User user) {
		String result = "";
		HttpStatus httpStatus = null;
		try {
			if (userService.checkLogin(user)) {
				result = jwtService.generateTokenLogin(user.getUsername());
				httpStatus = HttpStatus.OK;
			} else {
				result = "Wrong userId and password";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
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
	
	/*-----------------GET ALL DEVICES----------------------*/
	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	public ResponseEntity<List<Device>> getAllDevices() {
		return new ResponseEntity<List<Device>>(deviceService.findAllDevices(), HttpStatus.OK);
	}
	
	/*-----------------GET DEVICE BY ID---------------------*/
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getDeviceById(@PathVariable String id) {
		Device device = deviceService.findDeviceById(id);
		if (device != null) {
			return new ResponseEntity<Object>(device, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found Device", HttpStatus.NO_CONTENT);
	}
	
	/*------------------CREATE NEW DEVICE---------------------*/
	@RequestMapping(value = "/devices", method = RequestMethod.POST)
	public ResponseEntity<String> createDevice(@RequestBody Device device) {
		if (deviceService.addDevice(device)) {
			return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Device Existed!", HttpStatus.BAD_REQUEST);
		}
	}
	
	/* ---------------- DELETE DEVICE ------------------------ */
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteDeviceById(@PathVariable String id) {
		deviceService.deleteDevice(id);
		return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
	}

}
