package duke.model.locations;

import duke.commons.enumerations.Constraint;

import java.util.ArrayList;

/**
 * Class which models a train station.
 */
public class TrainStation extends RouteNode {
    private ArrayList<String> trainCode;

    /**
     * Creates a location object.
     *
     * @param address Name of train station
     * @param latitude latitude of train station
     * @param longitude longitude of train station
     */
    public TrainStation(ArrayList<String> trainCode, String description, String address,
                double latitude, double longitude) {
        super(Constraint.valueOf("MRT"), address, description, latitude, longitude);
        this.trainCode = trainCode;
    }

    public ArrayList<String> getTrainCode() {
        return trainCode;
    }
}
