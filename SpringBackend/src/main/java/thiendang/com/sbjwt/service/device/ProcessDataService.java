package thiendang.com.sbjwt.service.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProcessDataService {

    private ObjectMapper mapper = new ObjectMapper();

    public void writeObjectData(String path, Object deviceObj) {
        try {
            mapper.writeValue(new File(path), deviceObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
