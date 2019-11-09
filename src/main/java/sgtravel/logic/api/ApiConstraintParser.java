package sgtravel.logic.api;

import sgtravel.model.transports.TransportationMap;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.locations.Venue;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles all API constraint parsing.
 */
public class ApiConstraintParser {

    /**
     * Get nearest Bus Stop to location.
     *
     * @param place Starting location.
     * @param busStopMap Map of all Bus Stop.
     * @return The nearest Bus Stop.
     */
    public static BusStop getNearestBusStop(Venue place, HashMap<String, BusStop> busStopMap) {
        double minimumDisplacement = 1000;
        BusStop nearestBusStop = null;

        for (Map.Entry mapElement : busStopMap.entrySet()) {
            BusStop cur = (BusStop) mapElement.getValue();
            double displacement = getDisplacement(place, cur);
            if (displacement < minimumDisplacement) {
                minimumDisplacement = displacement;
                nearestBusStop = cur;
            }
        }

        return nearestBusStop;
    }

    /**
     * Gets the displacement between 2 Venues.
     *
     * @param start The start Venue.
     * @param end The end Venue.
     * @return displacement The displacement between both Venues.
     */
    private static double getDisplacement(Venue start, Venue end) {
        double displacement = Math.pow(Math.abs(start.getLatitude() - end.getLatitude()), 2)
                + Math.pow(Math.abs(start.getLongitude() - end.getLongitude()), 2);
        displacement = Math.sqrt(displacement);
        return displacement;
    }

    /**
     * Get nearest Train Station to location.
     *
     * @param place Starting location.
     * @param trainMap Map of all Train Station.
     * @return The nearest Train Station.
     */
    public static TrainStation getNearestTrainStation(Venue place, HashMap<String, TrainStation> trainMap) {
        double curMinDisplacement = 1000;
        TrainStation nearestTrainStation = null;

        for (Map.Entry mapElement : trainMap.entrySet()) {
            TrainStation cur = (TrainStation)mapElement.getValue();
            double displacement = getDisplacement(place, cur);
            if (displacement < curMinDisplacement) {
                curMinDisplacement = displacement;
                nearestTrainStation = cur;
            }
        }

        return nearestTrainStation;
    }

    /**
     * Return the nearest transportation from the starting location.
     *
     * @param start The starting location.
     * @param map All transportation location.
     * @return Nearest transportation.
     */
    public static Venue getNearestTransport(Venue start, TransportationMap map) {
        TrainStation nearestTrain = getNearestTrainStation(start, map.getTrainMap());
        BusStop nearestBus = getNearestBusStop(start, map.getBusStopMap());
        return nearestTransport(start, nearestTrain, nearestBus);
    }

    /**
     * Compares and returns the closer TrainStation or BusStop.
     *
     * @param venue The base Venue.
     * @param nearestTrain The nearest TrainStation.
     * @param nearestBus The nearest BusStop.
     * @return The closer TrainStation or BusStop.
     */
    private static Venue nearestTransport(Venue venue, TrainStation nearestTrain, BusStop nearestBus) {
        double displacementTrain;
        double displacementBus;
        displacementTrain = getDisplacement(venue, nearestTrain);
        displacementBus = getDisplacement(venue, nearestBus);

        if (displacementTrain <= displacementBus) {
            return nearestTrain;
        } else {
            return nearestBus;
        }
    }
}
