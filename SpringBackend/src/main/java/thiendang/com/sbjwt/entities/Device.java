package thiendang.com.sbjwt.entities;

import java.io.Serializable;

public class Device implements Serializable{
	private String id;
	private String resource;
	private String content;
	private String description;
	
	public Device(String id, String resource, String content, String description) {
		super();
		this.id = id;
		this.resource = resource;
		this.content = content;
		this.description = description;
	}
	
	public Device() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getResource() {
		return resource;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "AudioFlowsDiagnostic [id=" + id + ", resource=" + resource + ", content=" + content + ", description="
				+ description + "]";
	}
	
	
}
