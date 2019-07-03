package thiendang.com.sbjwt.entities;

import com.fasterxml.jackson.annotation.JsonView;
import thiendang.com.sbjwt.views.AttributeViews;

import java.io.Serializable;
import java.util.function.Supplier;

public class DeviceInformation implements Serializable, Supplier {

	@JsonView(AttributeViews.Version_Abtribute.class)
	private String current_version;
    @JsonView(AttributeViews.Version_Abtribute.class)
    private String emsfp_version;
    @JsonView(AttributeViews.Version_Abtribute.class)
    private String asic_version;
    @JsonView(AttributeViews.Version_Abtribute.class)
    private String hw_version;

	@JsonView(AttributeViews.Swshal_Type_Abtribute.class)
	private String sw_sha1;
    @JsonView(AttributeViews.Swshal_Type_Abtribute.class)
	private String type;

	@JsonView(AttributeViews.AsicSlot_Abtribute.class)
	private String asic_slot_00;
    @JsonView(AttributeViews.AsicSlot_Abtribute.class)
	private String asic_slot_01;
    @JsonView(AttributeViews.AsicSlot_Abtribute.class)
    private String asic_slot_02;
    @JsonView(AttributeViews.AsicSlot_Abtribute.class)
    private String asic_slot_03;

//    @Override
//    public String get() {
//        try{
//            Thread.sleep(500);
//        }catch (Exception e){}
//
//        return "data";
//    }

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
	public String getSw_sha1() {
		return sw_sha1;
	}
	public void setSw_sha1(String sw_shal) {
		this.sw_sha1 = sw_shal;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAsic_slot_00() {
		return asic_slot_00;
	}
	public void setAsic_slot_00(String asic_slot_00) {
		this.asic_slot_00 = asic_slot_00;
	}
	public String getAsic_slot_01() {
		return asic_slot_01;
	}
	public void setAsic_slot_01(String asic_slot_01) {
		this.asic_slot_01 = asic_slot_01;
	}
	public String getAsic_slot_02() {
		return asic_slot_02;
	}
	public void setAsic_slot_02(String asic_slot_02) {
		this.asic_slot_02 = asic_slot_02;
	}
	public String getAsic_slot_03() {
		return asic_slot_03;
	}
	public void setAsic_slot_03(String asic_slot_03) {
		this.asic_slot_03 = asic_slot_03;
	}
	public String getHw_version() {
		return hw_version;
	}
	public void setHw_version(String hw_version) {
		this.hw_version = hw_version;
	}
	public DeviceInformation(String current_version, String emsfp_version, String asic_version, String sw_shal, String type,
			String asic_slot_00, String asic_slot_01, String asic_slot_02, String asic_slot_03, String hw_version) {

		this.current_version = current_version;
		this.emsfp_version = emsfp_version;
		this.asic_version = asic_version;
		this.sw_sha1 = sw_shal;
		this.type = type;
		this.asic_slot_00 = asic_slot_00;
		this.asic_slot_01 = asic_slot_01;
		this.asic_slot_02 = asic_slot_02;
		this.asic_slot_03 = asic_slot_03;
		this.hw_version = hw_version;
	}
	public DeviceInformation() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DeviceInformation [current_version=" + current_version + ", emsfp_version=" + emsfp_version
				+ ", asic_version=" + asic_version + ", sw_shal=" + sw_sha1 + ", type=" + type + ", asic_slot_00="
				+ asic_slot_00 + ", asic_slot_01=" + asic_slot_01 + ", asic_slot_02=" + asic_slot_02 + ", asic_slot_03="
				+ asic_slot_03 + ", hw_version=" + hw_version + "]";
	}


	@Override
	public Object get() {
		return null;
	}
}
