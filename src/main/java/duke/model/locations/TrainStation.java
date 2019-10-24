package duke.model.locations;

import duke.commons.enumerations.Constraint;
import java.util.ArrayList;

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
    public TrainStation(ArrayList<String> trainCode, String description, String address,
                double latitude, double longitude) {
        super(Constraint.valueOf("MRT"), address, description, latitude, longitude);
        this.trainCodes = trainCode;
    }

    public ArrayList<String> getTrainCodes() {
        return trainCodes;
    }
}
