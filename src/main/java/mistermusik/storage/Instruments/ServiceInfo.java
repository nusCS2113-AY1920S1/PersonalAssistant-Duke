package mistermusik.storage.Instruments;
import mistermusik.commons.events.formatting.EventDate;

public class ServiceInfo {

	/**
	 * date of the servicing
	 */
    private EventDate serviceDate;
    
    /**
     * brief description of the servicing
     */
    private String serviceDescription;

    /**
     * Creates a Goal instance with the goal input by user and 
     * a boolean to check if goal is achieved
     *
     * @param description
     */
    public ServiceInfo(EventDate date, String description) {
        this.serviceDate = date;
        this.serviceDescription = description;
    }
    
    /**
     * Returns the details of the servicing
     * 
     * @return the details of the servicing.
     */
    public String getServiceInfo() {
    	return serviceDescription + " on " + serviceDate.getFormattedDateString();
    }

}
