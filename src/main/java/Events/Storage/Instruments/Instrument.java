package Events.Storage.Instruments;

import java.util.ArrayList;

import Events.EventTypes.Event;
import Events.Formatting.EventDate;
import Events.Storage.ServiceInfo;

public class Instrument {

    private String instrumentName;
    private ArrayList<ServiceInfo> serviceInfoList;
    

    /**
     * Creates an Instrument instance with the input name.
     *
     * @param name Name of instrument
     */
    public Instrument(String name) {
       this.instrumentName = name;
    }
    
    public String getName() {
    	return instrumentName;
    }

    public void addService (EventDate date, String description) {
    	ServiceInfo newServiceInfo = new ServiceInfo(date, description);
        serviceInfoList.add(newServiceInfo);
    }
    
    public String getServiceInfos() {
    	String res = "";
    	int j;
    	for (int i = 0; i < serviceInfoList.size(); i++) {
    		j = i+1;
    		res += j + serviceInfoList.get(i).getServiceInfo() + "\n";
    	}
    	return res;
    }

}
