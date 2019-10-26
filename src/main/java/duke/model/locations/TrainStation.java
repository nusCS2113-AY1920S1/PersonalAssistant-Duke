package duke.model.locations;

import duke.commons.enumerations.Constraint;
import duke.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing a Train Station.
 */
public class TrainStation extends RouteNode {
    private ArrayList<String> trainCodes;

    /**
     * Creates a TrainStation object.
     *
     * @param trainCode The ArrayList of train code Strings.
     * @param description The description of the train station.
     * @param address The name of train station.
     * @param latitude The latitude of train station.
     * @param longitude The longitude of train station.
     */
    public TrainStation(ArrayList<String> trainCode, String address, String description,
                double latitude, double longitude) {
        super(Constraint.valueOf("MRT"), address, description, latitude, longitude);
        this.trainCodes = trainCode;
    }

    public ArrayList<String> getTrainCodes() {
        return trainCodes;
    }

    /**
     * Fetches data from model and updates the Train Station.
     *
     * @param model The model.
     */
    public void fetchData(Model model) {
        HashMap<String, TrainStation> allTrainStations = model.getMap().getTrainMap();
        if (allTrainStations.containsKey(this.getAddress())) {
            this.setAddress(allTrainStations.get(this.getAddress()).getAddress());
            this.setLatitude(allTrainStations.get(this.getAddress()).getLatitude());
            this.setLongitude(allTrainStations.get(this.getAddress()).getLongitude());
        }
    }
}
