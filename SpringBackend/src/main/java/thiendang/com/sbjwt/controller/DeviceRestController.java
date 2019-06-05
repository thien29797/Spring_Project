//package thiendang.com.sbjwt.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import thiendang.com.dao.DeviceDAO;
//
//import thiendang.com.sbjwt.entities.Device;
//
//@RestController
//@RequestMapping("/restdiv")
//public class DeviceRestController {
//	
//	@Autowired
//    private DeviceDAO deviceDAO;
//	
//	@RequestMapping(value = "/devices", method = RequestMethod.GET)
//	public ResponseEntity<List<Device>> getAllDevices() {
//		return new ResponseEntity<List<Device>>(deviceDAO.findAllDevices(), HttpStatus.OK);
//	}
//	
//}
