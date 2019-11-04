package duke.logic.parsers;

import duke.commons.Messages;
import duke.commons.enumerations.Direction;
import duke.commons.exceptions.CategoryNotFoundException;
import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.ParseException;
import duke.model.Event;
import duke.model.planning.Todo;
import duke.model.locations.BusStop;
import duke.model.profile.ProfileCard;
import duke.model.transports.Route;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import duke.model.transports.BusService;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for Storage related operations.
 */
public class ParserStorageUtil {

    /**
     * Updates the profile with data from the storage.
     *
     * @param line The String description of an profile.
     */
    public static void createProfileFromStorage(ProfileCard profileCard, String line)
            throws ParseException {
        String[] token = line.split("\\|");
        switch (token[0].strip()) {
        case "person":
            profileCard.setPerson(token[1].strip(), ParserTimeUtil.parseStringToDate(token[2].strip()));
            break;
        case "preference":
            String[] category = {"", "sports", "entertainment", "arts", "lifestyle"};
            for (int i = 1; i < token.length; i++) {
                try {
                    profileCard.setPreference(category[i], token[i].strip().equals("true"));
                } catch (CategoryNotFoundException e) {
                    throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
                }
            }
            break;
        default:
            break;
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

    /**
     * Parses a ProfileCard to String format.
     * @param profileCard The profileCard.
     * @return profileString The corresponding String format of the profileCard object.
     */
    public static String toProfileStorageString(ProfileCard profileCard) {
        String profileString = "";
        profileString += "person | " + profileCard.getPersonName() + " | " + profileCard.getPersonBirthday() + "\n";

        profileString += "preference";
        for (Boolean i : profileCard.getPreference()) {
            profileString += " | " + i;
        }
        profileString += "\n";
        //profileString += "favorite | " + profileCard.toString()
        return profileString;
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
     * Parses part of a task back to a Location.
     */
    public static Venue getVenueFromStorage(String line) {
        String[] taskParts = line.split("\\|");
        String address = taskParts[0].strip();
        double longitude = Double.parseDouble(taskParts[1].strip());
        double latitude = Double.parseDouble(taskParts[2].strip());
        double distX = Double.parseDouble(taskParts[3].strip());
        double distY = Double.parseDouble(taskParts[4].strip());
        return new Venue(address, latitude, longitude, distX, distY);
    }

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
     * Parses a route from String format back to route.
     * @param line The String description of a route.
     * @return The corresponding Route object.
     */
    public static Route createRouteFromStorage(String line) {
        String[] details = line.split("\\|", 3);

        return new Route(new ArrayList<>(), details[1].strip(), details[2].strip());
    }

    /**
     * Parses a route node from String format back to a route node.
     * @param line The String description of a route node.
     * @return The corresponding RouteNode object.
     */
    public static RouteNode createNodeFromStorage(String line) throws CorruptedFileException {
        String[] details = line.split("\\|", 7);
        switch (details[1].strip()) {
        case "BUS":
            return new BusStop(details[2].strip(), details[3].strip(), details[4].strip(),
                    Double.parseDouble(details[5].strip()),  Double.parseDouble(details[6].strip()));
        case "MRT":
            return new TrainStation(new ArrayList<String>(), details[3].strip(), details[4].strip(),
                    Double.parseDouble(details[5].strip()),  Double.parseDouble(details[6].strip()));
        default:
            throw new CorruptedFileException("ROUTE.TXT");
        }
    }

    /**
     * Returns a list of todo's from a text file.
     *
     * @return The List of todo's.
     */
    public static List<Todo> getTodoListFromStorage(String line) {
        List<Todo> todoList = new ArrayList<>();
        String[] todoParts = line.split("\\|");
        for (String todoPart : todoParts) {
            Todo todo = new Todo(todoPart);
            todoList.add(todo);
        }
        return todoList;
    }

    public static int getNumberFromStorage(String line) {
        String[] recommendParts = line.split("\\|");
        return Integer.parseInt(recommendParts[1]);
    }
}
