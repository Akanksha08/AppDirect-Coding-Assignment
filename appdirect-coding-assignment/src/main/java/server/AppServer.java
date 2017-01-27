/**
 * 
 */
package server;

/**
 * @author akanksha
 *
 */
import integration.service.SubscriptionNotificationService;

import java.util.logging.Logger;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import product.service.BookService;

/**
 * @author akanksha
 *
 */
public class AppServer {
	private static Logger logger = Logger.getLogger(AppServer.class.getName());
	
	public static void main(String[] args) throws Exception {
		JAXRSServerFactoryBean serverFactory = new JAXRSServerFactoryBean();
		serverFactory.setResourceClasses(BookService.class, SubscriptionNotificationService.class);
		serverFactory.setAddress("http://192.168.0.100:9092/appdirect-challenge");
		serverFactory.setProvider(new JacksonJsonProvider());
		Server server = serverFactory.create();
		logger.info("Service is published at " + server.getDestination().getAddress().getAddress().getValue());
	}

}
