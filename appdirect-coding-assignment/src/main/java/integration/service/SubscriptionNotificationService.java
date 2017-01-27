package integration.service;

import integration.beans.ErrorResponse;
import integration.beans.Notification;
import integration.beans.Order;
import integration.beans.SuccessResponse;
import integration.beans.UserAccountDao;
import integration.beans.ErrorResponse.ErrorCode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Description(value = "Subscription Notification Service required to integrate with AppDirect")
@Path("/api/subscription/notification")
public class SubscriptionNotificationService {
	private static Logger logger = Logger.getLogger(SubscriptionNotificationService.class.getName());
	private static Properties securityProperties;
	
	static {
		//load security properties
		InputStream inStream = SubscriptionNotificationService.class.getClassLoader().getResourceAsStream("security.properties");
		securityProperties = new Properties();
		try {
			securityProperties.load(inStream);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unable to load properties", e);
		}
	}

	private UserAccountDao userAccounts;

	public SubscriptionNotificationService() {
		super();
		userAccounts = new UserAccountDao();
	}

	@GET
	@Path("/create")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Descriptions({ 
		@Description(value = "Subscribes user to a product", target = DocTarget.METHOD),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
	})
	public Response create(@QueryParam("url") String url) {
		logger.info("Create Subscription with event URL: " + url + " called");
		Notification notification;
		try {
			notification = getEventInformation(url);
			logger.info("Notification: " + notification.getType());
			
			String uuid = notification.getCreator().getUuid();
			
			//check if user already exists
			if (userAccounts.getUserAccountByUUID(uuid) != null) {
				return Response
						.status(200)
						.entity(new ErrorResponse("false", ErrorCode.USER_ALREADY_EXISTS, "User Already exists"))
						.type(MediaType.APPLICATION_JSON)
						.build();
			}
			
			// create new user account if not already there
            Order order = notification.getPayload().getOrder();
            String accountIdentifier = userAccounts.createUserAccount(uuid, order);
            
			return Response
					.status(200)
					.entity(new SuccessResponse("true", accountIdentifier))
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (Exception e) {
			return Response
					.status(200)
					.entity(new ErrorResponse("false", ErrorCode.UNKNOWN_ERROR, e.getMessage()))
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
	}
	
	@GET
	@Path("/change")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Descriptions({ 
		@Description(value = "Modifies subscription of a user to a product", target = DocTarget.METHOD),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
	})
	public Response change(@QueryParam("url") String url) {
		logger.info("Change Subscription with event URL: " + url + " called");
		Notification notification;
		try {
			notification = getEventInformation(url);
			logger.info("Notification: " + notification.getType());
			
			String accountIdentifier = notification.getPayload().getAccount().getAccountIdentifier();
			Order order = notification.getPayload().getOrder();
			
			// here upgrading the subscription by updating the order details
			if (userAccounts.updateUserAccount(accountIdentifier, order)) {
				return Response
						.status(200)
						.entity(new SuccessResponse("true", accountIdentifier))
						.type(MediaType.APPLICATION_JSON)
						.build();
			} else {
				return Response
						.status(200)
						.entity(new ErrorResponse("false", ErrorCode.ACCOUNT_NOT_FOUND, "Unable to change the subscription"))
						.type(MediaType.APPLICATION_JSON)
						.build();
			}
            
		} catch (Exception e) {
			return Response
					.status(200)
					.entity(new ErrorResponse("false", ErrorCode.UNKNOWN_ERROR, String.format("Exception thrown %s", e.getMessage())))
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
	}
	
	@GET
	@Path("/cancel")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@Descriptions({ 
		@Description(value = "Cancels subscription of user", target = DocTarget.METHOD),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
	})
	public Response cancel(@QueryParam("url") String url) {
		logger.info("Cancel Subscription with event URL: " + url + " called");
		Notification notification;
		try {
			notification = getEventInformation(url);
			logger.info("Notification: " + notification.getType());
			String accountIdentifier = notification.getPayload().getAccount().getAccountIdentifier();
			
            if (userAccounts.removeUserAccount(accountIdentifier)) {
            	return Response
    					.status(200)
    					.entity(new SuccessResponse("true", accountIdentifier))
    					.type(MediaType.APPLICATION_JSON)
    					.build();
            } else {
            	return Response
						.status(200)
						.entity(new ErrorResponse("false", ErrorCode.ACCOUNT_NOT_FOUND, "Unable to cancel the subscription"))
						.type(MediaType.APPLICATION_JSON)
						.build();
            }
			
		} catch (Exception e) {
			return Response
					.status(200)
					.entity(new ErrorResponse("false", ErrorCode.UNKNOWN_ERROR, String.format("Exception thrown %s", e.getMessage())))
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
	}

	/**
	 * getting event information using OAuth signed fetch
	 * @param url
	 * @return
	 */
	private Notification getEventInformation(String eventUrl) throws Exception {
		
		//creating Oauth Consumer
		OAuthConsumer consumer = new DefaultOAuthConsumer(securityProperties.getProperty("consumer.key"), securityProperties.getProperty("consumer.key"));
		
		//signing URL
		String signedUrl = consumer.sign(eventUrl);
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(signedUrl);
		request.addHeader("Accept", MediaType.APPLICATION_JSON);
		
		//signed fetch of notification event
		CloseableHttpResponse response = client.execute(request);
		System.out.println(response.getEntity().getContent());
		InputStream content = response.getEntity().getContent();
		
		BufferedInputStream in = new BufferedInputStream(content);
		in.mark(0);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(in, Notification.class);

	}

}
