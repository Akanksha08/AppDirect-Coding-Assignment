package integration.beans;
/**
 * @author akanksha
 *
 */
public class ErrorResponse {

	private String success; 
	private ErrorCode errorCode;
	private String message;
	
	public ErrorResponse(String success,
			ErrorCode errorCode,
			String message) {
		this.success = success;
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getSuccess() {
		return success;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	public enum ErrorCode {
		USER_ALREADY_EXISTS,
		USER_NOT_FOUND,
		ACCOUNT_NOT_FOUND,
		MAX_USERS_REACHED,
		UNAUTHORIZED,
		OPERATION_CANCELED,
		CONFIGURATION_ERROR,
		INVALID_RESPONSE,
		PENDING,
		FORBIDDEN,
		BINDING_NOT_FOUND,
		TRANSPORT_ERROR,
		UNKNOWN_ERROR
	}

}
