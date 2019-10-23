package duke.model.locations;

import duke.commons.enumerations.Constraint;

import java.util.ArrayList;

public class TrainStation extends RouteNode {
    private ArrayList<String> trainCode;

    /**
     * Creates a location object.
     *
     * @param address Name of train station
     * @param latitude latitude of train station
     * @param longitude longitude of train station
     */
    public TrainStation(ArrayList<String> trainCode, String description, String address, double latitude, double longitude) {
        super(Constraint.valueOf("MRT"), address, description, latitude, longitude);
        this.trainCode = trainCode;
    }

    //Constraint type, String address, String description, double latitude, double longitude

    public ArrayList<String> getTrainCode() {
        return trainCode;
    }

    public void fetchCoordinates() {

    }
}
