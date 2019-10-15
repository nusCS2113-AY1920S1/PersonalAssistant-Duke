package duke.logic;

import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiConstraintParser;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.model.transports.BusService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    public PathFinder(CreateMap map) throws DukeException {
        this.busStopMap = map.getBusStopMap();
        this.busMap = map.getBusMap();
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
    public ArrayList<BusStop> execute(Venue start, Venue end) {
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
