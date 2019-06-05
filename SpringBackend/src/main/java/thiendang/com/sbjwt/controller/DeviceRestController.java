package thiendang.com.sbjwt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import thiendang.com.dao.DeviceDAO;
import thiendang.com.sbjwt.entities.User;

@RestController
@RequestMapping("/restdiv")
public class DeviceRestController {
	
	@Autowired
    private DeviceDAO deviceDAO;
	
	
}
