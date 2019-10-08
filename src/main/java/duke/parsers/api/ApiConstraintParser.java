package duke.parsers.api;

import duke.data.BusStop;
import duke.data.Location;
import duke.commons.exceptions.DukeException;
import duke.data.tasks.Holiday;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle all API constraint parsing.
 */
public class ApiConstraintParser {

    /**
     * Parses the userInput and return a new Holiday constructed from it.
     *
     * @param holiday The holiday object which needs to subject to constraint
     * @param constraint The constraint to be applied
     * @return The updated Holiday object with constraint
     */
    public static Holiday getConstraintLocation(Holiday holiday, String constraint) throws DukeException {
        switch (constraint) {
        case "onlyBus":
            return getBus(holiday);
        case "onlyMRT":
            return getMrt(holiday);
        case "Hybrid":
            return getHybrid(holiday);
        default:
            return holiday;
        }
    }

    private static Holiday getHybrid(Holiday holiday) throws DukeException {

        // Make calls to the API and get the new coordinates of the longitude and latitude of the nearest constraint;

        /*
        double latitude = 1; dummy value
        double longitude = 0; dummy value
        holiday.setLocation(holiday.getLocation().setLatitude(latitude));
        holiday.setLocation(holiday.getLocation().setLongitude(longitude));
        */

        return holiday;
    }

    private static Holiday getBus(Holiday holiday) {

        // Make calls to the API and get the new coordinates of the longitude and latitude of the nearest busstop;

        /*
        double latitude = 1; dummy value
        double longitude = 0; dummy value
        holiday.setLocation(holiday.getLocation().setLatitude(latitude));
        holiday.setLocation(holiday.getLocation().setLongitude(longitude));
        */


        return holiday;
    }

    private static Holiday getMrt(Holiday holiday) {

        // Make calls to the API and get the new coordinates of the longitude and latitude of the nearest mrt;

        /*
        double latitude = 1; dummy value
        double longitude = 0; dummy value
        holiday.setLocation(holiday.getLocation().setLatitude(latitude));
        holiday.setLocation(holiday.getLocation().setLongitude(longitude));
        */

        return holiday;
    }

    /**
     * Get nearest bus stop to location.
     * @param place Starting location
     * @param busStopMap Map of all bus stop
     * @return nearest bus stop
     */
    public static BusStop getNearestBusStop(Location place, HashMap<String, BusStop> busStopMap) {
        double placeLatitude = place.getLatitude();
        double placeLongitude = place.getLongitude();
        double minimumDisplacement = 1000;
        BusStop nearestBusStop = null;
        for (Map.Entry mapElement : busStopMap.entrySet()) {
            BusStop cur = (BusStop)mapElement.getValue();
            //no idea why the lang and long is swap
            double displacement = Math.pow(Math.abs(placeLatitude - cur.getLongitude()), 2)
                                + Math.pow(Math.abs(placeLongitude - cur.getLatitude()), 2);
            displacement = Math.sqrt(displacement);
            if (displacement < minimumDisplacement) {
                minimumDisplacement = displacement;
                nearestBusStop = cur;
            }
        }

        return nearestBusStop;
    }

}
