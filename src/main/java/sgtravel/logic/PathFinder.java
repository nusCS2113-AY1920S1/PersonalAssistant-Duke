package sgtravel.logic;

import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.enumerations.Direction;
import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.commons.exceptions.RouteGenerateFailException;
import sgtravel.logic.api.ApiConstraintParser;
import sgtravel.model.Model;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.CustomNode;
import sgtravel.model.locations.RouteNode;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.locations.Venue;
import sgtravel.model.transports.BusService;
import sgtravel.model.transports.TransportationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines an algorithm to find a path between 2 Venues.
 */
public class PathFinder {
    private TransportationMap map;
    private HashSet<BusStop> visited;
    private HashMap<String, String> path;
    private boolean found = false;

    /**
     * Initialise Pathfinder object.
     *
     */
    public PathFinder(TransportationMap map) {
        this.map = map;
        this.visited = new HashSet<>();
        this.path = new HashMap<>();
    }

    /**
     * Find path between start and end.
     *
     * @param start starting location.
     * @param end ending location.
     * @return path.
     */
    public ArrayList<Venue> execute(Venue start, Venue end, Constraint constraint) throws RouteGenerateFailException {
        found = false;
        switch (constraint) {
        case BUS:
            return findBusRoute(start, end);
        case MRT:
            return findTrainRoute(start, end);
        default:
            throw new RouteGenerateFailException();
        }
    }

    private ArrayList<Venue> findTrainRoute(Venue start, Venue end) {
        this.found = false;
        TrainStation startTrainStation = ApiConstraintParser.getNearestTrainStation(start, this.map.getTrainMap());
        TrainStation endTrainStation = ApiConstraintParser.getNearestTrainStation(end, this.map.getTrainMap());
        ArrayList<Venue> path = new ArrayList<>();

        if (isSameLocation(startTrainStation, endTrainStation)) {
            path.add(start);
            path.add(end);
            return path;
        }

        if (!isSameLocation(start, startTrainStation)) {
            path.add(start);
        }
        path.add(startTrainStation);
        if (!onSameLine(startTrainStation, endTrainStation)) {
            ArrayList<TrainStation> curTrainLine;
            for (String line : startTrainStation.getTrainCodes()) {
                curTrainLine = this.map.getTrainLine(line.substring(0,2));
                assert curTrainLine != null : "Train Code does not exist";
                for (TrainStation trainStation : curTrainLine) {
                    if (onSameLine(trainStation, endTrainStation)) {
                        path.add(trainStation);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        } else {
            found = true;
        }


        path.add(endTrainStation);

        if (!isSameLocation(end, endTrainStation)) {
            path.add(end);
        }

        if (found) {
            return path;
        } else {
            return null;
        }

    }

    private boolean isSameLocation(Venue start, Venue end) {
        return start.equals(end);
    }

    private boolean onSameLine(TrainStation cur, TrainStation endTrainStation) {
        for (String code : cur.getTrainCodes()) {
            for (String code2 : endTrainStation.getTrainCodes()) {
                if (code2.contains(code.substring(0,2))) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Venue> findBusRoute(Venue start, Venue end) {
        this.found = false;
        BusStop startBusStop = ApiConstraintParser.getNearestBusStop(start, this.map.getBusStopMap());
        BusStop endBusStop = ApiConstraintParser.getNearestBusStop(end, this.map.getBusStopMap());
        ArrayList<Venue> ans = new ArrayList<>();

        if (isSameLocation(startBusStop, endBusStop)) {
            ans.add(start);
            ans.add(end);
            return ans;
        }

        BusStop cur = startBusStop;
        int depthLimit = 0;

        while (!found && depthLimit < 3) {
            this.visited.clear();
            this.path.clear();
            depthFirstSearch(cur, endBusStop, depthLimit);
            depthLimit += 1;
        }

        if (!this.found) {
            return null;
        } else {
            cur = endBusStop;
            if (!isSameLocation(end, endBusStop)) {
                ans.add(end);
            }
            while (!cur.getBusCode().equals(startBusStop.getBusCode())) {
                ans.add(cur);
                cur = this.map.getBusStopMap().get(path.get(cur.getBusCode()));
            }
            ans.add(cur);
            if (!isSameLocation(start, startBusStop)) {
                ans.add(start);
            }
            return ans;
        }
    }

    private void depthFirstSearch(BusStop cur, BusStop endBusStop, int depthLimit) {
        if (depthLimit == 0 || this.visited.contains(cur)) {
            return;
        }

        this.visited.add(cur);

        for (String bus : cur.getBuses()) { //loop through all bus in bus stop
            Direction direction;
            if (this.map.getBusMap().get(bus).getDirection(Direction.FORWARD).contains(cur.getBusCode())) {
                direction = Direction.FORWARD;
            } else {
                direction = Direction.BACKWARD;
            }

            for (String busCode : this.map.getBusMap().get(bus).getDirection(direction)) { // depth search the bus route

                if (this.found) {
                    break;
                }

                if (busCode.equals(cur.getBusCode())) {
                    continue;
                }

                path.put(busCode, cur.getBusCode());
                if (haveSameBus(this.map.getBusStopMap().get(busCode), endBusStop)) {
                    path.put(endBusStop.getBusCode(), busCode);
                    this.found = true;
                    return;
                } else {
                    depthFirstSearch(this.map.getBusStopMap().get(busCode), endBusStop, depthLimit - 1);
                }
            }
        }
    }

    /**
     * Returns if two BusStops have a common Bus Service.
     *
     * @param firstBusStop The first BusStop object.
     * @param secondBusStop The second BusStop object.
     * @return true If the two BusStops have a common Bus Service.
     */
    private boolean haveSameBus(BusStop firstBusStop, BusStop secondBusStop) {
        for (String bus : firstBusStop.getBuses()) {
            if (secondBusStop.getBuses().contains(bus)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a custom RouteNode from a venue.
     *
     * @param venue The venue.
     * @return The custom RouteNode created.
     */
    public static RouteNode generateCustomRouteNode(Venue venue) {
        return new CustomNode(venue.getAddress(), "", venue.getLatitude(), venue.getLongitude());
    }

    /**
     * Generates an ArrayList of Venues between 2 Venues.
     *
     * @param startVenue The starting Venue.
     * @param endVenue The ending Venue.
     * @param model The model object containing information about the user.
     * @return result The ArrayList of Venues.
     * @throws QueryFailedException If a TrainStation cannot be queried.
     */
    public static ArrayList<Venue> generateInbetweenNodes(Venue startVenue, Venue endVenue, Model model)
            throws QueryFailedException {
        ArrayList<Venue> result = new ArrayList<>();

        if (startVenue instanceof BusStop && endVenue instanceof BusStop) {
            result = generateInbetweenBusRoutes(startVenue, endVenue, model);
        } else if (startVenue instanceof TrainStation && endVenue instanceof TrainStation) {
            result = generateInbetweenTrainRoutes((TrainStation) startVenue, (TrainStation) endVenue, model);
        }

        return result;
    }

    /**
     * Generates an ArrayList of BusStops between 2 Bus Stops.
     *
     * @param startVenue The starting Venue.
     * @param endVenue The ending Venue.
     * @param model The model object containing information about the user.
     * @return result The ArrayList of BusStops.
     */
    private static ArrayList<Venue> generateInbetweenBusRoutes(Venue startVenue, Venue endVenue, Model model)
            throws QueryFailedException {
        boolean isGenerated = false;
        ArrayList<Venue> result = new ArrayList<>();
        TransportationMap map = model.getMap();
        HashMap<String, BusService> busMap = map.getBusMap();
        Set<String> busServices = ((BusStop) startVenue).getBuses();

        for (String busNumber: busServices) {
            if (!isGenerated) {
                BusService bus = busMap.get(busNumber);
                ArrayList<String> busCodes = bus.getDirection(Direction.FORWARD);

                result =
                        searchForwardDirectionBus((BusStop) startVenue, (BusStop) endVenue, busNumber, busCodes, model);
                if (result != null) {
                    isGenerated = true;
                } else {
                    Collections.reverse(busCodes);
                    result =
                        searchReverseDirectionBus((BusStop) startVenue, (BusStop) endVenue, busNumber, busCodes, model);
                    if (result != null) {
                        isGenerated = true;
                    }
                }

            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Searches the forward direction of a given BusCode ArrayList to find the start and end venue.
     *
     * @param startVenue The start Venue.
     * @param endVenue The end Venue.
     * @param busNumber The bus service number.
     * @param busCodes The ArrayList of bus stop codes.
     * @param model The model object containing information about the user.
     * @return result The ArrayList of BusStops.
     * @throws QueryFailedException If the bus stop cannot be found.
     */
    private static ArrayList<Venue> searchForwardDirectionBus(BusStop startVenue, BusStop endVenue,
                              String busNumber, ArrayList<String> busCodes, Model model) throws QueryFailedException {
        boolean isStartNodeFound = false;
        boolean isGenerated = false;
        ArrayList<Venue> result = new ArrayList<>();
        String startNodeBusCode = startVenue.getBusCode();
        String endNodeBusCode = endVenue.getBusCode();

        for (String busCode : busCodes) {
            if (endNodeBusCode.equals(busCode) && !isStartNodeFound) {
                break;
            }

            if (endNodeBusCode.equals(busCode) && isStartNodeFound) {
                isGenerated = true;
                break;
            }

            if (isStartNodeFound) {
                BusStop node = new BusStop(busCode, "", "", 0, 0);
                result.add(node);
            }

            if (startNodeBusCode.equals(busCode)) {
                BusStop node = new BusStop(busCode, "", "", 0, 0);
                node.fetchData(model);
                result.add(new CustomNode("Bus Service " + busNumber, "", node.getLatitude(),
                        node.getLongitude()));
                isStartNodeFound = true;
            }
        }

        if (isGenerated) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * Searches the reverse direction of a given BusCode ArrayList to find the start and end venue.
     *
     * @param startVenue The start Venue.
     * @param endVenue The end Venue.
     * @param busNumber The bus service number.
     * @param busCodes The ArrayList of bus stop codes.
     * @param model The model object containing information about the user.
     * @return result The ArrayList of BusStops.
     * @throws QueryFailedException If the bus stop cannot be found.
     */
    private static ArrayList<Venue> searchReverseDirectionBus(BusStop startVenue, BusStop endVenue,
                              String busNumber, ArrayList<String> busCodes, Model model) throws QueryFailedException {
        boolean isStartNodeFound = false;
        boolean isGenerated = false;
        ArrayList<Venue> result = new ArrayList<>();
        String startNodeBusCode = startVenue.getBusCode();
        String endNodeBusCode = endVenue.getBusCode();

        for (String busCode : busCodes) {
            if (endNodeBusCode.equals(busCode) && isStartNodeFound) {
                isGenerated = true;
                break;
            }

            if (isStartNodeFound) {
                BusStop node = new BusStop(busCode, "", "", 0, 0);
                result.add(node);
            }

            if (startNodeBusCode.equals(busCode)) {
                BusStop node = new BusStop(busCode, "", "", 0, 0);
                node.fetchData(model);
                result.add(new CustomNode("Bus Service " + busNumber, "", node.getLatitude(),
                        node.getLongitude()));
                isStartNodeFound = true;
            }
        }

        if (isGenerated) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * Generates an ArrayList of TrainStations between 2 Bus Stops.
     *
     * @param startVenue The starting Venue.
     * @param endVenue The ending Venue.
     * @param model The model object containing information about the user.
     * @return result The ArrayList of TrainStations.
     */
    private static ArrayList<Venue> generateInbetweenTrainRoutes(TrainStation startVenue, TrainStation endVenue,
                                                                 Model model) throws QueryFailedException {
        boolean isGenerated = false;
        ArrayList<Venue> result = new ArrayList<>();
        TransportationMap map = model.getMap();
        String startNodeDescription = startVenue.getDescription();
        TrainStation start = map.getTrainStation(startNodeDescription);
        ArrayList<String> trainCodes = start.getTrainCodes();

        for (String trainCode: trainCodes) {
            if (!isGenerated) {
                String lineCode = trainCode.substring(0, 2);
                ArrayList<TrainStation> trainLine = map.getTrainLine(lineCode);

                result = searchForwardDirectionTrain(startVenue, endVenue, trainCode, trainLine);
                if (result != null) {
                    isGenerated = true;
                } else {
                    Collections.reverse(trainLine);
                    result = searchReverseDirectionTrain(startVenue, endVenue, trainCode, trainLine);
                    if (result != null) {
                        isGenerated = true;
                    }
                }
            } else {
                break;
            }
        }


        return result;
    }

    /**
     * Searches the forward direction of a train line to find the start and end venue.
     *
     * @param startVenue The start venue.
     * @param endVenue The end venue.
     * @param trainCode The train code.
     * @param trainLine The train line
     * @return result The ArrayList of train stations.
     */
    private static ArrayList<Venue> searchForwardDirectionTrain(TrainStation startVenue, TrainStation endVenue,
                               String trainCode, ArrayList<TrainStation> trainLine) {
        boolean isStartNodeFound = false;
        boolean isGenerated = false;
        ArrayList<Venue> result = new ArrayList<>();
        String startNodeDescription = startVenue.getDescription();
        String endNodeDescription = endVenue.getDescription();

        for (TrainStation trainStation : trainLine) {
            String newNodeDescription = trainStation.getDescription();
            if (endNodeDescription.equals(newNodeDescription) && !isStartNodeFound) {
                break;
            }

            if (endNodeDescription.equals(newNodeDescription) && isStartNodeFound) {
                isGenerated = true;
                break;
            }

            if (isStartNodeFound) {
                result.add(trainStation);
            }

            if (startNodeDescription.equals(newNodeDescription)) {
                result.add(new CustomNode(trainCode + " Line", "", trainStation.getLatitude(),
                        trainStation.getLongitude()));
                isStartNodeFound = true;
            }
        }

        if (isGenerated) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * Searches the reverse direction of a train line to find the start and end venue.
     *
     * @param startVenue The start venue.
     * @param endVenue The end venue.
     * @param trainCode The train code.
     * @param trainLine The train line
     * @return result The ArrayList of train stations.
     */
    private static ArrayList<Venue> searchReverseDirectionTrain(TrainStation startVenue, TrainStation endVenue,
                                                                String trainCode, ArrayList<TrainStation> trainLine) {
        boolean isStartNodeFound = false;
        boolean isGenerated = false;
        ArrayList<Venue> result = new ArrayList<>();
        String startNodeDescription = startVenue.getDescription();
        String endNodeDescription = endVenue.getDescription();

        for (TrainStation trainStation : trainLine) {
            String newNodeDescription = trainStation.getDescription();
            if (endNodeDescription.equals(newNodeDescription) && isStartNodeFound) {
                isGenerated = true;
                break;
            }

            if (isStartNodeFound) {
                result.add(trainStation);
            }

            if (startNodeDescription.equals(newNodeDescription)) {
                result.add(new CustomNode(trainCode + " Line", "", trainStation.getLatitude(),
                        trainStation.getLongitude()));
                isStartNodeFound = true;
            }
        }

        if (isGenerated) {
            return result;
        } else {
            return null;
        }
    }
}
