package sgtravel.model.locations;

import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.model.Model;
import sgtravel.model.transports.TransportationMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a train station.
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

    /**
     * Alternative constructor which automatically uses fetchData().
     *
     * @param trainStationName The bus code.
     * @param model The model object containing information about the user.
     */
    public TrainStation(String trainStationName, Model model) throws QueryFailedException {
        super(Constraint.valueOf("MRT"), "", "", 0, 0);
        this.setAddress(trainStationName);
        fetchData(model);
    }

    /**
     * Sets the trainCodes from an ArrayList of Strings.
     *
     * @param trainCodes The ArrayList of Strings.
     */
    public void setTrainCodes(ArrayList<String> trainCodes) {
        this.trainCodes = trainCodes;
    }

    /**
     * Gets the ArrayList of train code of the train station in String.
     *
     * @return trainCodes The ArrayList of train code of the train station in String.
     */
    public ArrayList<String> getTrainCodes() {
        return trainCodes;
    }

    /**
     * Fetches data from model and updates the Train Station.
     *
     * @param model The model.
     */
    public void fetchData(Model model) throws QueryFailedException {
        String[] addressDetails = this.getAddress().split(" ");
        StringBuilder addressSB = new StringBuilder();
        for (String detail : addressDetails) {
            addressSB.append(detail.toUpperCase(), 0, 1).append(detail.substring(1)).append(" ");
        }
        String address = addressSB.toString();
        address = address.substring(0, address.length() - 1);

        TransportationMap map = model.getMap();
        HashMap<String, TrainStation> allTrainStations = map.getTrainMap();
        if (allTrainStations.containsKey(address)) {
            TrainStation node = allTrainStations.get(address);
            this.setAddress(address + " MRT");
            this.setLatitude(node.getLatitude());
            this.setLongitude(node.getLongitude());
        } else {
            throw new QueryFailedException(address);
        }
    }
}
