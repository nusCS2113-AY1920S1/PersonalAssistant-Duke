package duke.logic.api;

import duke.logic.CreateMap;
import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles all API constraint parsing.
 */
public class ApiConstraintParser {

    /**
     * Get nearest bus stop to location.
     * @param place Starting location
     * @param busStopMap Map of all bus stop
     * @return nearest bus stop
     */
    public static BusStop getNearestBusStop(Venue place, HashMap<String, BusStop> busStopMap) {
        double minimumDisplacement = 1000;
        BusStop nearestBusStop = null;
        for (Map.Entry mapElement : busStopMap.entrySet()) {
            BusStop cur = (BusStop)mapElement.getValue();
            double displacement = getDisplacement(place, cur);
            if (displacement < minimumDisplacement) {
                minimumDisplacement = displacement;
                nearestBusStop = cur;
            }
        }

        return nearestBusStop;
    }

    private static double getDisplacement(Venue start, Venue end) {
        double displacement = Math.pow(Math.abs(start.getLatitude() - end.getLatitude()), 2)
                + Math.pow(Math.abs(start.getLongitude() - end.getLongitude()), 2);
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
        double minimumDisplacement = 1000;
        TrainStation nearestTrainStation = null;

        for (Map.Entry mapElement : trainMap.entrySet()) {
            TrainStation cur = (TrainStation)mapElement.getValue();
            double displacement = getDisplacement(place, cur);
            if (displacement < minimumDisplacement) {
                minimumDisplacement = displacement;
                nearestTrainStation = cur;
            }
        }

        return nearestTrainStation;
    }

    /**
     * Return the nearest transportation from the starting location.
     * @param start The  starting location
     * @param map All transportation location
     * @return Nearest transportation
     */
    public static Venue getNearestTransport(Venue start, CreateMap map) {
        TrainStation nearestTrain = getNearestTrainStation(start, map.getTrainMap());
        BusStop nearestBus = getNearestBusStop(start, map.getBusStopMap());
        return nearestTransport(start, nearestTrain, nearestBus);
    }

    private static Venue nearestTransport(Venue start, TrainStation nearestTrain, BusStop nearestBus) {
        double displacementTrain;
        double displacementBus;
        displacementTrain = getDisplacement(start, nearestTrain);
        displacementBus = getDisplacement(start, nearestBus);
        if (displacementTrain <= displacementBus) {
            return  nearestTrain;
        } else {
            return nearestBus;
        }
    }
}
