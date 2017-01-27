package integration.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author akanksha
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

	private String name;
	private String phoneNumber;
	private String uuid;
	private String website;
	
	@JsonCreator
	public Company(
			@JsonProperty("name") String name,
			@JsonProperty("phoneNumber") String phoneNumber,
			@JsonProperty("uuid") String uuid,
			@JsonProperty("website") String website) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.uuid = uuid;
		this.website = website;
	}
	
	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getUuid() {
		return uuid;
	}

	public String getWebsite() {
		return website;
	}


}