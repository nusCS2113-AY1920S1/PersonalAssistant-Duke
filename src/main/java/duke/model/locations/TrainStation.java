package duke.model.locations;

import java.util.ArrayList;

public class TrainStation extends Venue {
    private ArrayList<String> trainCode;

    /**
     * Creates a location object.
     *
     * @param address Name of train station
     * @param latitude latitude of train station
     * @param longitude longitude of train station
     */
    public TrainStation(ArrayList<String> trainCode, String address, double latitude, double longitude) {
        super(address, latitude, longitude, 0, 0);
        this.trainCode = trainCode;
    }

    public ArrayList<String> getTrainCode() {
        return trainCode;
    }
}
