package duke.logic;

import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiParser;
import duke.model.locations.BusStop;
import duke.model.transports.BusService;

import java.util.HashMap;
import java.util.Map;

public class CreateMap {
    private HashMap<String, BusService> busMap;
    private HashMap<String, BusStop> busStopMap;

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
}
