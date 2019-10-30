package Events.Storage.Instruments;

import java.util.ArrayList;

public class InstrumentList {

    private ArrayList<Instrument> instrumentList;

//    private boolean isAchieved;

    /**
     * Creates a Goal instance with the goal input by user and a boolean to check if goal is achieved.
     *
     * @param description
     */
    public InstrumentList() {}

    public String getInstruments() {
    	String res = "";
    	int j;
    	for (int i = 0; i < instrumentList.size(); i++) {
    		j = i+1;
    		res += j + instrumentList.get(i).getName() + "\n";
    	}
    	return res;
    }
    
    public void addInstrument(String name) {
    	Instrument newInstrument = new Instrument(name);
    	instrumentList.add(newInstrument);
    }
    
    public String getInstrumentServiceInfo(int index) {
    	int j = index - 1;
    	return instrumentList.get(j).toString();
    }

}
