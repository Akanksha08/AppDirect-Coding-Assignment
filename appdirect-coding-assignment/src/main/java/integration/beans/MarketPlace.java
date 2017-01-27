package integration.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akanksha
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketPlace {

	private String baseUrl;
	private String partner;

	@JsonCreator
	public MarketPlace(@JsonProperty("baseUrl") String baseUrl,
			@JsonProperty("partner") String partner) {
		this.baseUrl = baseUrl;
		this.partner = partner;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getPartner() {
		return partner;
	}

}