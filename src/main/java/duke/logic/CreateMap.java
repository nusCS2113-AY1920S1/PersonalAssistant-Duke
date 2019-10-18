package duke.logic;

import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiParser;
import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.transports.BusService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CreateMap {
    private HashMap<String, BusService> busMap;
    private HashMap<String, BusStop> busStopMap;
    private HashMap<String, TrainStation> trainMap;
    private ArrayList<TrainStation> northEastLine;
    private ArrayList<TrainStation> northSouthLine;
    private ArrayList<TrainStation> circleLine;
    private ArrayList<TrainStation> downtownLine;
    private ArrayList<TrainStation> eastWestLine;

    /**
     * Initialise createMap object with both busStopMap and busMap.
     * @param busStopMap Map of busStop object
     * @param busMap Map of BusService object
     */
    public CreateMap(HashMap<String, BusStop> busStopMap, HashMap<String, BusService> busMap) {
        this.busMap = busMap;
        this.busStopMap = busStopMap;
    }

    /**
     * Initialise createMap object.
     */
    public CreateMap() throws DukeException {
        this.busMap = ApiParser.getBusRoute();
        this.busStopMap = ApiParser.getBusStop();
        fillBusStop();
    }

    private void fillBusStop() {
        for (Map.Entry mapElement : this.busMap.entrySet()) {
            String bus = (String)mapElement.getKey();
            BusService busService = (BusService)mapElement.getValue();
            for (String busCode : busService.getDirection(1)) {
                if (busStopMap.containsKey(busCode)) {
                    busStopMap.get(busCode).addBuses(bus);
                }
            }
        }
    }

    public HashMap<String, BusService> getBusMap() {
        return this.busMap;
    }

    public HashMap<String, BusStop> getBusStopMap() {
        return this.busStopMap;
    }

    public void setTrainMap(HashMap<String, TrainStation> trainMap) {
        this.trainMap = trainMap;
        setTrainLine();
    }

    private void setTrainLine() {
        ArrayList<TrainStation> northEastLine = new ArrayList<>();
        ArrayList<TrainStation> northSouthLine = new ArrayList<>();
        ArrayList<TrainStation> circleLine = new ArrayList<>();
        ArrayList<TrainStation> downtownLine = new ArrayList<>();
        ArrayList<TrainStation> eastWestLine = new ArrayList<>();
        for (Map.Entry mapElement : this.trainMap.entrySet()) {
            TrainStation trainStation = (TrainStation)mapElement.getValue();
            for (String trainCode : trainStation.getTrainCode()) {
                if (trainCode.contains("NE")) {
                    northEastLine.add(trainStation);
                }
                if (trainCode.contains("NS")) {
                    northSouthLine.add(trainStation);
                }
                if (trainCode.contains("EW")) {
                    eastWestLine.add(trainStation);
                }
                if (trainCode.contains("CC")) {
                    circleLine.add(trainStation);
                }
                if (trainCode.contains("DT")) {
                    downtownLine.add(trainStation);
                }

            }
        }
        this.northEastLine = sortTrainLine(northEastLine, "NE");
        this.northSouthLine = sortTrainLine(northSouthLine, "NS");
        this.circleLine = sortTrainLine(circleLine, "CC");
        this.downtownLine = sortTrainLine(downtownLine, "DT");
        this.eastWestLine = sortTrainLine(eastWestLine, "EW");
    }

    private ArrayList<TrainStation> sortTrainLine(ArrayList<TrainStation> trainLine, String trainLineCode) {
        trainLine.sort(new SortByTrainCode(trainLineCode));
        return trainLine;
    }

    /**
     * Return mrt line of corresponding code.
     * @param lineCode Code of mrt line
     * @return mrt line
     */
    public ArrayList<TrainStation> getTrainLine(String lineCode) {
        switch (lineCode) {
        case "NE":
            return this.northEastLine;
        case "NS":
            return this.northSouthLine;
        case "CC":
            return this.circleLine;
        case "EW":
            return this.eastWestLine;
        case "DT":
            return this.downtownLine;
        default:
            return null;
        }
    }

    static class SortByTrainCode implements Comparator<TrainStation> {
        private String trainLine;

        SortByTrainCode(String trainLine) {
            this.trainLine = trainLine;
        }

        @Override
        public int compare(TrainStation o1, TrainStation o2) {
            String trainCode1 = getTrainCode(o1, this.trainLine);
            String trainCode2 = getTrainCode(o2, this.trainLine);
            assert trainCode1 != null : "Train Station in wrong line";
            assert trainCode2 != null : "Train Station in wrong line";
            return trainCode1.compareTo(trainCode2);
        }

        private String getTrainCode(TrainStation o1, String trainLine) {
            for (String trainCode : o1.getTrainCode()) {
                if (trainCode.contains(trainLine)) {
                    return trainCode;
                }
            }
            return null;
        }
    }


    public HashMap<String, TrainStation> getTrainMap() {
        return trainMap;
    }
}
