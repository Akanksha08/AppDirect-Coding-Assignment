package integration.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akanksha
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    private Type type;
	private MarketPlace marketplace;
    private Creator creator;
    private Payload payload;
    
    @JsonCreator
    public Notification(@JsonProperty("type") Type type,
    		@JsonProperty("marketplace") MarketPlace marketplace,
    		@JsonProperty("creator") Creator creator,
    		@JsonProperty("payload") Payload payload) {
        this.type = type;
        this.marketplace = marketplace;
        this.creator = creator;
        this.payload = payload;
    }

    public enum Type {
        SUBSCRIPTION_ORDER, // create subscription
        SUBSCRIPTION_CHANGE,
        SUBSCRIPTION_CANCEL
    }
    
    public Type getType() {
		return type;
	}

	public MarketPlace getMarketplace() {
		return marketplace;
	}

	public Creator getCreator() {
		return creator;
	}

	public Payload getPayload() {
		return payload;
	}

}