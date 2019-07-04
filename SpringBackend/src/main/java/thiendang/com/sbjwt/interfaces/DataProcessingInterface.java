package thiendang.com.sbjwt.interfaces;

public interface DataProcessingInterface {

    // Export object data to file
    void writeObjectData(Object obj);

    // Import object data from file
    Object readObjectData();


}
