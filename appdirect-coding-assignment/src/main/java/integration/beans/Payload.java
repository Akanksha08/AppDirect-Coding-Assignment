package integration.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author akanksha
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

	private Company company;
	private Order order;
	private Account account;
	
	@JsonCreator
	public Payload(
			@JsonProperty("account") Account account,
			@JsonProperty("company") Company company,
			@JsonProperty("order") Order order) {
		this.account = account;
		this.company = company;
		this.order = order;
	}

	public Company getCompany() {
		return company;
	}

	public Order getOrder() {
		return order;
	}

	public Account getAccount() {
		return account;
	}

}