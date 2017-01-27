package integration.beans;
/**
 * @author akanksha
 *
 */
public class SuccessResponse {
	
	private String success;
	private String accountIdentifier;
	
    public SuccessResponse(String success,
    		String accountIdentifier) {
        this.success = success;
        this.accountIdentifier = accountIdentifier;
    }
    
    public String getSuccess() {
		return success;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}
	
}
