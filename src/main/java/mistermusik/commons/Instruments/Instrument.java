package mistermusik.commons.Instruments;

import mistermusik.commons.events.formatting.EventDate;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Instrument {
    private static Logger logger = Logger.getLogger("Instrument");
    private String instrumentName;
    private ArrayList<ServiceInfo> serviceInfoList;
    

    /**
     * Creates an Instrument instance with the input name.
     *
     * @param name Name of instrument
     */
    public Instrument(String name) {
       this.instrumentName = name;
       serviceInfoList = new ArrayList<>();
    }
    
    public String getName() {
    	return instrumentName;
    }

    public int addService (EventDate date, String description) {
    	ServiceInfo newServiceInfo = new ServiceInfo(date, description);
        serviceInfoList.add(newServiceInfo);
//        logger.log(Level.INFO, "The new service info is added to the list");
        return serviceInfoList.size();
    }
    
    public String getServiceInfos() {
    	String res = "";
    	int j;
    	for (int i = 0; i < serviceInfoList.size(); i++) {
    		j = i+1;
    		res += j + ". " + serviceInfoList.get(i).getServiceInfo() + "\n";
    	}
    	return res;
    }
    
    public String getIndexAndService(int index) {
    	return index + ". " + serviceInfoList.get(index-1).getServiceInfo();
    }

}
