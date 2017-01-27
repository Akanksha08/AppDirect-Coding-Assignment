package integration.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akanksha
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    private String editionCode;
    private String pricingDuration;
    
    @JsonCreator
    public Order(@JsonProperty("editionCode") String editionCode,
    		@JsonProperty("pricingDuration") String pricingDuration) {
        this.editionCode = editionCode;
        this.pricingDuration = pricingDuration;
    }
	
	public String getEditionCode() {
		return editionCode;
	}

	public String getPricingDuration() {
		return pricingDuration;
	}
	
}