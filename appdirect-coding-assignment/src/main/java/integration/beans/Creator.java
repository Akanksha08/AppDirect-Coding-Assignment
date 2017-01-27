package integration.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author akanksha
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Creator {

	private String firstName;
	private String lastName;
	private String email;
	private String language;
	private String openId;
	private String uuid;
	private String locale;
	
	@JsonCreator
	public Creator(@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("email") String email,
			@JsonProperty("language") String language,
			@JsonProperty("uuid") String uuid,
			@JsonProperty("openId") String openId,
			@JsonProperty("locale") String locale
			) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.language = language;
		this.openId = openId;
		this.uuid = uuid;
		this.locale = locale;
	}
	
	public String getLocale() {
		return locale;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getLanguage() {
		return language;
	}

	public String getOpenId() {
		return openId;
	}

	public String getUuid() {
		return uuid;
	}

}