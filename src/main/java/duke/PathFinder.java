package duke;

import duke.commons.exceptions.DukeException;
import duke.data.BusService;
import duke.data.BusStop;
import duke.data.Location;
import duke.parsers.api.ApiConstraintParser;
import duke.parsers.api.ApiParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PathFinder {
    private HashMap<String, BusService> busMap;
    private HashMap<String, BusStop> busStopMap;
    private HashSet<BusStop> visited;
    private HashMap<String, String> path;
    private boolean found = false;

    /**
     * Initialise Pathfinder object.
     *
     */
    public PathFinder() throws DukeException {
        this.busMap = ApiParser.getBusRoute();
        this.busStopMap = ApiParser.getBusStop();
        this.visited = new HashSet<>();
        this.path = new HashMap<>();
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


    /**
     * Find path between start and end.
     *
     * @param start starting location
     * @param end ending location
     * @return path
     */
    public ArrayList<BusStop> execute(Location start, Location end) {
        BusStop startBusStop = ApiConstraintParser.getNearestBusStop(start, this.busStopMap);
        BusStop endBusStop = ApiConstraintParser.getNearestBusStop(end, this.busStopMap);

        BusStop cur = startBusStop;
        int depthLimit = 0;

        while (!found && depthLimit < 3) {
            this.visited.clear();
            this.path.clear();
            depthFirstSearch(cur, endBusStop, depthLimit);
            depthLimit += 1;
        }
        if (!this.found) {
            return new ArrayList<>();
        } else {
            cur = endBusStop;
            ArrayList<BusStop> ans = new ArrayList<>();
            while (!cur.getBusCode().equals(startBusStop.getBusCode())) {
                ans.add(cur);
                cur = busStopMap.get(path.get(cur.getBusCode()));
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
            for (String busCode : this.busMap.get(bus).getDirection(1)) { // depth search the bus route
                if (!this.found) {
                    if (busCode.equals(cur.getBusCode())) {
                        continue;
                    }
                    path.put(busCode, cur.getBusCode());
                    if (haveSameBus(this.busStopMap.get(busCode), endBusStop)) {
                        path.put(endBusStop.getBusCode(), busCode);
                        this.found = true;
                        return;
                    } else {
                        depthFirstSearch(this.busStopMap.get(busCode), endBusStop, depthLimit - 1);
                    }
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
