package duke.logic;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiConstraintParser;
import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PathFinder {
    private CreateMap map;
    private HashSet<BusStop> visited;
    private HashMap<String, String> path;
    private Constraint constraint;
    private boolean found = false;

    /**
     * Initialise Pathfinder object.
     *
     */
    public PathFinder(CreateMap map) throws DukeException {
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
    public ArrayList<Venue> execute(Venue start, Venue end, Constraint constraint) {
        found = false;
        System.out.println("run");
        switch (constraint) {
        case BUS:
            return findBusRoute(start, end);
        case MRT:
            return findTrainRoute(start, end);
        default:
            return findMixedRoute(start, end);
        }
    }

    private ArrayList<Venue> findMixedRoute(Venue start, Venue end) {
        return null;
    }

    private ArrayList<Venue> findTrainRoute(Venue start, Venue end) {
        TrainStation startTrainStation = ApiConstraintParser.getNearestTrainStation(start, this.map.getTrainMap());
        TrainStation endTrainStation = ApiConstraintParser.getNearestTrainStation(end, this.map.getTrainMap());
        ArrayList<Venue> path = new ArrayList<>();
        path.add(startTrainStation);
        if (!onSameLine(startTrainStation, endTrainStation)) {
            ArrayList<TrainStation> curTrainLine;
            for (String line : startTrainStation.getTrainCode()) {
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
        }
        path.add(endTrainStation);
        if (found) {
            return path;
        } else {
            return null;
        }

    }

    private boolean onSameLine(TrainStation cur, TrainStation endTrainStation) {
        for (String code : cur.getTrainCode()) {
            for (String code2 : endTrainStation.getTrainCode()) {
                if (code2.contains(code.substring(0,2))) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Venue> findBusRoute(Venue start, Venue end) {
        BusStop startBusStop = ApiConstraintParser.getNearestBusStop(start, this.map.getBusStopMap());
        BusStop endBusStop = ApiConstraintParser.getNearestBusStop(end, this.map.getBusStopMap());

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
            ArrayList<Venue> ans = new ArrayList<>();
            while (!cur.getBusCode().equals(startBusStop.getBusCode())) {
                ans.add(cur);
                cur = this.map.getBusStopMap().get(path.get(cur.getBusCode()));
            }
            ans.add(cur);
            return ans;
        }
    }

    private void depthFirstSearch(BusStop cur, BusStop endBusStop, int depthLimit) {

        if (depthLimit == 0 || this.visited.contains(cur)) {
            return;
        }

        this.visited.add(cur);

        for (String bus : cur.getBuses()) { //loop through all bus in bus stop
            for (String busCode : this.map.getBusMap().get(bus).getDirection(1)) { // depth search the bus route
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

    private boolean haveSameBus(BusStop cur, BusStop endBusStop) {
        for (String bus : cur.getBuses()) {
            if (endBusStop.getBuses().contains(bus)) {
                return true;
            }
        }
        return false;
    }


}
