package duke.parsers.api;

import duke.commons.exceptions.DukeException;
import duke.data.tasks.Holiday;

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

}
