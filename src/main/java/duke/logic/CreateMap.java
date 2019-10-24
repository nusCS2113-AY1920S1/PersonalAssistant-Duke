package duke.logic;

import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.transports.BusService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the transportation map of the Singapore transport network
 */
public class CreateMap {
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
     * Initialise createMap object with both busStopMap and busMap.
     * @param busStopMap Map of busStop object
     * @param busMap Map of BusService object
     */
    public CreateMap(HashMap<String, BusStop> busStopMap, HashMap<String, BusService> busMap) {
        this.busMap = busMap;
        this.busStopMap = busStopMap;
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
        ArrayList<TrainStation> circleLineSub = new ArrayList<>();
        ArrayList<TrainStation> downtownLine = new ArrayList<>();
        ArrayList<TrainStation> eastWestLine = new ArrayList<>();
        ArrayList<TrainStation> eastWestLineSub = new ArrayList<>();

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
        this.northEastLine = sortTrainLine(northEastLine, "NE");
        this.northSouthLine = sortTrainLine(northSouthLine, "NS");
        this.circleLine = sortTrainLine(circleLine, "CC");
        this.circleLineSub = sortTrainLine(circleLineSub, "CE");
        this.downtownLine = sortTrainLine(downtownLine, "DT");
        this.eastWestLine = sortTrainLine(eastWestLine, "EW");
        this.eastWestLineSub = sortTrainLine(eastWestLineSub, "CG");
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
            for (String trainCode : o1.getTrainCode()) {
                if (trainCode.contains(trainLine)) {
                    return Integer.parseInt(trainCode.substring(2));
                }
            }
            return -1;
        }
    }


    public HashMap<String, TrainStation> getTrainMap() {
        return trainMap;
    }
}
