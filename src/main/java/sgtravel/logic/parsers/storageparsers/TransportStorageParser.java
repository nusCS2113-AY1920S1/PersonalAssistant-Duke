package sgtravel.logic.parsers.storageparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.enumerations.Direction;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.RouteNode;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.transports.BusService;
import sgtravel.model.transports.Route;

import java.util.ArrayList;

/**
 * Storage parser for the Transports.
 */
public class TransportStorageParser {
    /**
     * Parses a train station from String format back to Train.
     *
     * @param line The String description of a train station.
     * @return The corresponding Train object.
     */
    public static TrainStation createTrainFromStorage(String line) {
        String[] trainParts = line.split("\\|");
        String address = trainParts[0].strip();
        double longitude = Double.parseDouble(trainParts[2].strip());
        double latitude = Double.parseDouble(trainParts[1].strip());
        ArrayList<String> trainCode = new ArrayList<>();
        for (int i = 3; i < trainParts.length; i++) {
            trainCode.add(trainParts[i].strip());
        }
        return new TrainStation(trainCode, null, address, latitude, longitude);
    }

    /**
     * Parses a bus from String format back to BusService.
     *
     * @param line The String description of a bus.
     * @return The corresponding BusService object.
     */
    public static BusService createBusFromStorage(String line) {
        String[] busData = line.split("\\|");
        Direction direction = Direction.FORWARD;
        BusService busService = new BusService(busData[0].strip());
        for (int i = 1; i < busData.length; i++) {
            String buffer = busData[i].strip();
            if ("change".equals(buffer)) {
                direction = Direction.BACKWARD;
            }
            busService.addRoute(buffer, direction);
        }
        return busService;
    }

    /**
     * Parses a bus stop from String format back to BusStop.
     *
     * @param line The String description of a bus stop.
     * @return The corresponding BusStop object.
     */
    public static BusStop createBusStopDataFromStorage(String line) {
        String[] busStopData = line.split("\\|");
        String busCode = busStopData[0].strip();
        String address = busStopData[1].strip();
        String description = busStopData[2].strip();
        double latitude = Double.parseDouble(busStopData[3].strip());
        double longitude = Double.parseDouble(busStopData[4].strip());
        BusStop busStop = new BusStop(busCode, address, description, latitude, longitude);
        for (int i = 5; i < busStopData.length; i++) {
            busStop.addBuses(busStopData[i].strip());
        }
        return busStop;
    }

    /**
     * Parses a route node from String format back to a route node.
     * @param line The String description of a route node.
     * @return The corresponding RouteNode object.
     * @throws ParseException If data is corrupted.
     */
    public static RouteNode createNodeFromStorage(String line) throws ParseException {
        String[] details = line.split("\\|", 7);
        try {
            switch (details[1].strip()) {
            case "BUS":
                return new BusStop(details[2].strip(), details[3].strip(), details[4].strip(),
                        Double.parseDouble(details[5].strip()), Double.parseDouble(details[6].strip()));
            case "MRT":
                return new TrainStation(new ArrayList<>(), details[3].strip(), details[4].strip(),
                        Double.parseDouble(details[5].strip()), Double.parseDouble(details[6].strip()));
            default:
                throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
        }
    }

    /**
     * Parses a route from String format back to route.
     * @param line The String description of a route.
     * @return The corresponding Route object.
     * @throws ParseException If data is corrupted.
     */
    public static Route createRouteFromStorage(String line) throws ParseException {
        try {
            String[] details = line.split("\\|", 3);

            return new Route(new ArrayList<>(), details[1].strip(), details[2].strip());
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
        }
    }

    /**
     * Parses a route with its nodes from routes to String format.
     * @param route The route.
     * @return routeString The corresponding String format of the route object.
     */
    public static String toRouteStorageString(Route route) {
        String routeString = "";
        routeString += "route | " + route.getName() + " | " + route.getDescription() + "\n";

        for (RouteNode node: route.getNodes()) {
            if (node instanceof BusStop) {
                routeString += "node | BUS | " + ((BusStop) node).getBusCode() + " | " + node.getAddress() + " | "
                        + node.getDescription() + " | " + node.getLatitude() + " | " + node.getLongitude() + "\n";
            } else if (node instanceof TrainStation) {
                routeString += "node | MRT | " + ((TrainStation) node).getTrainCodes() + " | " + node.getAddress()
                        + " | " + node.getDescription() + " | " + node.getLatitude() + " | " + node.getLongitude()
                        + "\n";
            }
        }
        return routeString;
    }
}
