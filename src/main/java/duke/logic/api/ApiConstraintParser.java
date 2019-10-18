package duke.logic.api;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeException;
import duke.model.events.Event;
import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle all API constraint parsing.
 */
public class ApiConstraintParser {

    /**
     * Get nearest bus stop to location.
     * @param place Starting location
     * @param busStopMap Map of all bus stop
     * @return nearest bus stop
     */
    public static BusStop getNearestBusStop(Venue place, HashMap<String, BusStop> busStopMap) {
        double placeLatitude = place.getLatitude();
        double placeLongitude = place.getLongitude();
        double minimumDisplacement = 1000;
        BusStop nearestBusStop = null;
        for (Map.Entry mapElement : busStopMap.entrySet()) {
            BusStop cur = (BusStop)mapElement.getValue();
            double displacement = getDisplacement(placeLatitude, placeLongitude, cur.getLatitude(), cur.getLongitude());
            if (displacement < minimumDisplacement) {
                minimumDisplacement = displacement;
                nearestBusStop = cur;
            }
        }

        return nearestBusStop;
    }

    private static double getDisplacement(double latitude, double longitude, double curLatitude, double curLongitude) {
        double displacement = Math.pow(Math.abs(latitude - curLatitude), 2)
                + Math.pow(Math.abs(longitude - curLongitude), 2);
        displacement = Math.sqrt(displacement);
        return displacement;
    }

    /**
     * Get nearest Train Station to location.
     * @param place Starting location
     * @param trainMap Map of all Train Station
     * @return nearest Train Station
     */
    public static TrainStation getNearestTrainStation(Venue place, HashMap<String, TrainStation> trainMap) {
        double placeLatitude = place.getLatitude();
        double placeLongitude = place.getLongitude();
        double minimumDisplacement = 1000;
        TrainStation nearestTrainStation = null;
        for (Map.Entry mapElement : trainMap.entrySet()) {
            TrainStation cur = (TrainStation)mapElement.getValue();
            double displacement = getDisplacement(placeLatitude, placeLongitude, cur.getLatitude(), cur.getLongitude());
            if (displacement < minimumDisplacement) {
                minimumDisplacement = displacement;
                nearestTrainStation = cur;
            }
        }

        return nearestTrainStation;
    }
}
