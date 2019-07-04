package thiendang.com.sbjwt.entities;

import java.io.Serializable;

public class DeviceVersionInformation implements Serializable {

    private String current_version;
    private String emsfp_version;
    private String asic_version;
    private String hw_version;

    public String getCurrent_version() {
        return current_version;
    }

    public void setCurrent_version(String current_version) {
        this.current_version = current_version;
    }

    public String getEmsfp_version() {
        return emsfp_version;
    }

    public void setEmsfp_version(String emsfp_version) {
        this.emsfp_version = emsfp_version;
    }

    public String getAsic_version() {
        return asic_version;
    }

    public void setAsic_version(String asic_version) {
        this.asic_version = asic_version;
    }

    public String getHw_version() {
        return hw_version;
    }

    public void setHw_version(String hw_version) {
        this.hw_version = hw_version;
    }

    public DeviceVersionInformation(String current_version, String emsfp_version, String asic_version, String hw_version) {
        this.current_version = current_version;
        this.emsfp_version = emsfp_version;
        this.asic_version = asic_version;
        this.hw_version = hw_version;
    }

    public DeviceVersionInformation() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "DeviceVersionInformation{" +
                "current_version='" + current_version + '\'' +
                ", emsfp_version='" + emsfp_version + '\'' +
                ", asic_version='" + asic_version + '\'' +
                ", hw_version='" + hw_version + '\'' +
                '}';
    }
}
