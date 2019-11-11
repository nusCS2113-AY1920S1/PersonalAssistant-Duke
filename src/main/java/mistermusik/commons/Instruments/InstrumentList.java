package mistermusik.commons.Instruments;

import mistermusik.commons.events.formatting.EventDate;

import java.util.ArrayList;

public class InstrumentList {
    private ArrayList<Instrument> instrumentList;

    /**
     * Creates a Goal instance with the goal input by user and a boolean to check if goal is achieved.
     */
    public InstrumentList() {
        instrumentList = new ArrayList<>();
    }

    /**
     * Returns the instruments.
     */
    public String getInstruments() {
        String res = "";
        int j;
        for (int i = 0; i < instrumentList.size(); i++) {
            j = i + 1;
            res += j + ". " + instrumentList.get(i).getName() + "\n";
        }
        return res;
    }

    /**
     * Adds an instrument.
     *
     * @param name The name of the instrument.
     * @return The size of the instrument list.
     */
    public int addInstrument(String name) {
        Instrument newInstrument = new Instrument(name);
        instrumentList.add(newInstrument);
        return instrumentList.size();
    }

    /**
     * Gets the info of service list.
     *
     * @param index The index of instrument.
     * @return The info.
     */
    public String getInstrumentServiceInfo(int index) {
        int indexInList = index - 1;
        return instrumentList.get(indexInList).getServiceInfos();
    }

    /**
     * Adds a service.
     *
     * @param index       The instrument index.
     * @param date        The date.
     * @param description The description.
     * @return The service.
     */
    public int service(int index, EventDate date, String description) {
        int indexInList = index - 1;
        return instrumentList.get(indexInList).addService(date, description);
    }

    /**
     * Gets instrument.
     *
     * @param index Tje instrument index.
     * @return The instrument.
     */
    public String getIndexAndInstrument(int index) {
        return index + ". " + instrumentList.get(index - 1).getName();
    }

    /**
     * Gets service.
     *
     * @param instrumentIndex Index of the instrument.
     * @param serviceIndex    Index of the service.
     * @return The service.
     */
    public String getIndexAndService(int instrumentIndex, int serviceIndex) {
        return instrumentList.get(instrumentIndex - 1).getIndexAndService(serviceIndex);
    }

}
