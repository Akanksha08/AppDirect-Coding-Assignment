package integration.beans;

/**
 * @author akanksha
 *
 */
public class UserAccount {
	
	private String accountIdentifier;
	private String uuid;
	private Order subscription;
	
	public UserAccount(String accountIdentifier, String uuid, Order subscription) {
		super();
		this.accountIdentifier = accountIdentifier;
		this.uuid = uuid;
		this.subscription = subscription;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}
	
	public String getUuid() {
		return uuid;
	}

	public Order getSubscription() {
		return subscription;
	}
	
	public void setSubscription(Order subscription) {
		this.subscription = subscription;
	}

}