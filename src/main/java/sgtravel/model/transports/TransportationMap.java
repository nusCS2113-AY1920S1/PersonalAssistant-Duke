package sgtravel.model.transports;

import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.TrainStation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the transportation map of the Singapore transport network.
 */
public class TransportationMap {
    private HashMap<String, BusService> busMap;
    private HashMap<String, BusStop> busStopMap;
    private HashMap<String, TrainStation> trainMap;
    private ArrayList<TrainStation> northEastLine;
    private ArrayList<TrainStation> northSouthLine;
    private ArrayList<TrainStation> circleLine;
    private ArrayList<TrainStation> circleLineSub;
    private ArrayList<TrainStation> downtownLine;
    private ArrayList<TrainStation> eastWestLine;
    private ArrayList<TrainStation> eastWestLineSub;

    /**
     * Constructor for TransportationMap object with both busStopMap and busMap.
     *
     * @param busStopMap Map of BusStop objects.
     * @param busMap Map of BusService objects.
     */
    public TransportationMap(HashMap<String, BusStop> busStopMap, HashMap<String, BusService> busMap) {
        this.busMap = busMap;
        this.busStopMap = busStopMap;
    }

    /**
     * Sorts all TrainStations by TrainCode.
     */
    static class SortByTrainCode implements Comparator<TrainStation> {
        private String trainLine;

        SortByTrainCode(String trainLine) {
            this.trainLine = trainLine;
        }

        @Override
        public int compare(TrainStation o1, TrainStation o2) {
            int trainCodeNumber = getTrainCodeNumber(o1, this.trainLine);
            int trainCodeNumber2 = getTrainCodeNumber(o2, this.trainLine);
            assert trainCodeNumber != -1 : "Train Station in wrong line";
            assert trainCodeNumber2 != -1 : "Train Station in wrong line";
            return trainCodeNumber - trainCodeNumber2;
        }

        private int getTrainCodeNumber(TrainStation o1, String trainLine) {
            for (String trainCode : o1.getTrainCodes()) {
                if (trainCode.contains(trainLine)) {
                    return Integer.parseInt(trainCode.substring(2));
                }
            }
            return -1;
        }
    }

    public HashMap<String, BusService> getBusMap() {
        return this.busMap;
    }

    public HashMap<String, BusStop> getBusStopMap() {
        return this.busStopMap;
    }

    /**
     * Gets an ArrayList of TrainStation with the corresponding TrainLine code.
     *
     * @param trainLineCode Code of mrt line
     * @return mrt line
     */
    public ArrayList<TrainStation> getTrainLine(String trainLineCode) {
        switch (trainLineCode) {
        case "NE":
            return this.northEastLine;
        case "NS":
            return this.northSouthLine;
        case "CC":
            return this.circleLine;
        case "CE":
            return this.circleLineSub;
        case "EW":
            return this.eastWestLine;
        case "CG":
            return this.eastWestLineSub;
        case "DT":
            return this.downtownLine;
        default:
            return null;
        }
    }

    /**
     * Gets a sorted ArrayList of TrainStations of a specific TrainLine.
     *
     * @param trainStations The ArrayList of TrainStations to get.
     * @param trainLineCode The TrainLine code.
     * @return trainStations The sorted ArrayList of TrainStations of the line.
     */
    private ArrayList<TrainStation> getSortedTrainLine(ArrayList<TrainStation> trainStations, String trainLineCode) {
        trainStations.sort(new SortByTrainCode(trainLineCode));
        return trainStations;
    }

    public HashMap<String, TrainStation> getTrainMap() {
        return trainMap;
    }

    /**
     * Gets a TrainStation from the map.
     *
     * @param query The TrainStation name.
     * @return The TrainStation.
     * @throws QueryFailedException If the TrainStation cannot be found.
     */
    public TrainStation getTrainStation(String query) throws QueryFailedException {
        if (trainMap.containsKey(query)) {
            return trainMap.get(query);
        }

        throw new QueryFailedException("TRAIN_STATION");
    }

    /**
     * Sets the TrainMap according to a HashMap of key String and value TrainStation.
     *
     * @param trainMap The TrainMap to set.
     */
    public void setTrainMap(HashMap<String, TrainStation> trainMap) {
        this.trainMap = trainMap;
        setTrainLine();
    }

    /**
     * Sets the TrainLines for the TrainMap.
     */
    private void setTrainLine() {
        ArrayList<TrainStation> northEastLine = new ArrayList<>();
        ArrayList<TrainStation> northSouthLine = new ArrayList<>();
        ArrayList<TrainStation> circleLine = new ArrayList<>();
        ArrayList<TrainStation> circleLineSub = new ArrayList<>();
        ArrayList<TrainStation> downtownLine = new ArrayList<>();
        ArrayList<TrainStation> eastWestLine = new ArrayList<>();
        ArrayList<TrainStation> eastWestLineSub = new ArrayList<>();

        for (Map.Entry mapElement : this.trainMap.entrySet()) {
            TrainStation trainStation = (TrainStation)mapElement.getValue();
            for (String trainCode : trainStation.getTrainCodes()) {
                if (trainCode.contains("NE")) {
                    northEastLine.add(trainStation);
                }
                if (trainCode.contains("NS")) {
                    northSouthLine.add(trainStation);
                }
                if (trainCode.contains("EW")) {
                    eastWestLine.add(trainStation);
                }
                if (trainCode.contains("CG")) {
                    eastWestLineSub.add(trainStation);
                }
                if (trainCode.contains("CC")) {
                    circleLine.add(trainStation);
                }
                if (trainCode.contains("CE")) {
                    circleLineSub.add(trainStation);
                }
                if (trainCode.contains("DT")) {
                    downtownLine.add(trainStation);
                }

            }
        }
        this.northEastLine = getSortedTrainLine(northEastLine, "NE");
        this.northSouthLine = getSortedTrainLine(northSouthLine, "NS");
        this.circleLine = getSortedTrainLine(circleLine, "CC");
        this.circleLineSub = getSortedTrainLine(circleLineSub, "CE");
        this.downtownLine = getSortedTrainLine(downtownLine, "DT");
        this.eastWestLine = getSortedTrainLine(eastWestLine, "EW");
        this.eastWestLineSub = getSortedTrainLine(eastWestLineSub, "CG");
    }
}
