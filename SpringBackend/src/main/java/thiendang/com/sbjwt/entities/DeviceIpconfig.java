package thiendang.com.sbjwt.entities;

import java.io.Serializable;

public class DeviceIpconfig implements Serializable{

	private String version;
	private String local_mac;
	private String ip_addr;
	private String subnet_mask;
	private String gateway;
	private String hostname;
	private int port;
	private int dhcp_enable;
	private int ctl_vlan_id;
	private int ctl_vlan_pcp;
	private int ctl_vlan_enable;
	private int data_vlan_id;
	private int data_vlan_enable;
	private String bootstatus1;
	private String bootstatus2;

	public DeviceIpconfig(String version, String local_mac, String ip_addr, String subnet_mask, String gateway,
			String hostname, int port, int dhcp_enable, int ctl_vlan_id, int ctl_vlan_pcp, int ctl_vlan_enable,
			int data_vlan_id, int data_vlan_enable, String bootstatus1, String bootstatus2) {

		this.version = version;
		this.local_mac = local_mac;
		this.ip_addr = ip_addr;
		this.subnet_mask = subnet_mask;
		this.gateway = gateway;
		this.hostname = hostname;
		this.port = port;
		this.dhcp_enable = dhcp_enable;
		this.ctl_vlan_id = ctl_vlan_id;
		this.ctl_vlan_pcp = ctl_vlan_pcp;
		this.ctl_vlan_enable = ctl_vlan_enable;
		this.data_vlan_id = data_vlan_id;
		this.data_vlan_enable = data_vlan_enable;
		this.bootstatus1 = bootstatus1;
		this.bootstatus2 = bootstatus2;
	}

	public DeviceIpconfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLocal_mac() {
		return local_mac;
	}

	public void setLocal_mac(String local_mac) {
		this.local_mac = local_mac;
	}

	public String getIp_addr() {
		return ip_addr;
	}

	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}

	public String getSubnet_mask() {
		return subnet_mask;
	}

	public void setSubnet_mask(String subnet_mask) {
		this.subnet_mask = subnet_mask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getDhcp_enable() {
		return dhcp_enable;
	}

	public void setDhcp_enable(int dhcp_enable) {
		this.dhcp_enable = dhcp_enable;
	}

	public int getCtl_vlan_id() {
		return ctl_vlan_id;
	}

	public void setCtl_vlan_id(int ctl_vlan_id) {
		this.ctl_vlan_id = ctl_vlan_id;
	}
	
	public int getCtl_vlan_pcp() {
		return ctl_vlan_pcp;
	}

	public void setCtl_vlan_pcp(int ctl_vlan_pcp) {
		this.ctl_vlan_pcp = ctl_vlan_pcp;
	}

	public int getCtl_vlan_enable() {
		return ctl_vlan_enable;
	}

	public void setCtl_vlan_enable(int ctl_vlan_enable) {
		this.ctl_vlan_enable = ctl_vlan_enable;
	}

	public int getData_vlan_id() {
		return data_vlan_id;
	}

	public void setData_vlan_id(int data_vlan_id) {
		this.data_vlan_id = data_vlan_id;
	}

	public int getData_vlan_enable() {
		return data_vlan_enable;
	}

	public void setData_vlan_enable(int data_vlan_enable) {
		this.data_vlan_enable = data_vlan_enable;
	}

	public String getBootstatus1() {
		return bootstatus1;
	}

	public void setBootstatus1(String bootstatus1) {
		this.bootstatus1 = bootstatus1;
	}

	public String getBootstatus2() {
		return bootstatus2;
	}

	public void setBootstatus2(String bootstatus2) {
		this.bootstatus2 = bootstatus2;
	}

	@Override
	public String toString() {
		return "DeviceIpconfig [version=" + version + ", local_mac=" + local_mac + ", ip_addr=" + ip_addr
				+ ", subnet_mask=" + subnet_mask + ", gateway=" + gateway + ", hostname=" + hostname + ", port=" + port
				+ ", dhcp_enable=" + dhcp_enable + ", ctl_vlan_ip=" + ctl_vlan_id + ", ctl_vlan_pcp=" + ctl_vlan_pcp
				+ ", ctl_vlan_enable=" + ctl_vlan_enable + ", data_vlan_id=" + data_vlan_id + ", data_vlan_enable="
				+ data_vlan_enable + ", bootstatus1=" + bootstatus1 + ", bootstatus2=" + bootstatus2 + "]";
	}
	
}
