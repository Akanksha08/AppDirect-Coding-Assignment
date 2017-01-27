package integration.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author akanksha
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	private String accountIdentifier;
	private String status;

	@JsonCreator
	public Account(@JsonProperty("accountIdentifier") String accountIdentifier,
			@JsonProperty("status") String status) {
		this.accountIdentifier = accountIdentifier;
		this.status = status;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public String getStatus() {
		return status;
	}

}