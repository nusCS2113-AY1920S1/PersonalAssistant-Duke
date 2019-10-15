package duke.logic.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import duke.commons.Messages;
import duke.logic.api.requests.LocationSearchUrlRequest;
import duke.commons.exceptions.DukeException;
import duke.logic.api.requests.DataMallHttpRequest;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.model.transports.BusService;

import java.util.HashMap;

/**
 * Class to handle all API requests.
 */
public class ApiParser {

    /**
     * Return names and coordinates of location search.
     *
     * @param param The query
     * @return result The locations found
     */
    public static Venue getLocationSearch(String param) throws DukeException {
        LocationSearchUrlRequest req = new LocationSearchUrlRequest(param);
        JsonObject jsonRes = req.execute();
        JsonArray arr = jsonRes.getAsJsonArray("results");
        if (isFound(jsonRes)) {
            return new Venue(arr.get(0).getAsJsonObject().get("ADDRESS").getAsString(),
                    arr.get(0).getAsJsonObject().get("LATITUDE").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("LONGITUDE").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("X").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("Y").getAsDouble());
        }
        throw new DukeException(Messages.DATA_NOT_FOUND);
    }

    private static boolean isFound(JsonObject jsonRes) {
        return Integer.parseInt(String.valueOf(jsonRes.getAsJsonPrimitive("found"))) > 0;
    }

    /**
     * Return all bus stop in Singapore.
     *
     * @return List of Bus Stop
     */
    public static HashMap<String, BusStop> getBusStop() throws DukeException {
        String path = "BusStops";
        int skip = 0;
        HashMap<String, BusStop> allBus = new HashMap<>();
        while (skip < 5500) {
            DataMallHttpRequest req = new DataMallHttpRequest("BusStops", path, Integer.toString(skip));
            skip += 500;
            JsonObject jsonRes = req.execute();
            JsonArray arr = jsonRes.getAsJsonArray("value");
            for (int i = 0; i < arr.size(); i++) {
                BusStop busstop = new BusStop(
                        arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(),
                        arr.get(i).getAsJsonObject().get("Description").getAsString(),
                        arr.get(i).getAsJsonObject().get("RoadName").getAsString(),
                        arr.get(i).getAsJsonObject().get("Latitude").getAsDouble(),
                        arr.get(i).getAsJsonObject().get("Longitude").getAsDouble());
                allBus.put(arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(), busstop);
            }
        }

        return allBus;
    }

    /**
     * Return all bus route in Singapore.
     *
     * @return bus route
     */
    public static HashMap<String, BusService> getBusRoute() throws DukeException {
        String path = "BusRoutes";
        int skip = 0;
        HashMap<String, BusService> busMap = new HashMap<>();
        while (skip < 26000) {
            DataMallHttpRequest req = new DataMallHttpRequest("BusRoutes", path, Integer.toString(skip));
            skip += 500;
            JsonObject jsonRes = req.execute();
            JsonArray arr = jsonRes.getAsJsonArray("value");
            for (int i = 0; i < arr.size(); i++) {
                String serviceNo = arr.get(i).getAsJsonObject().get("ServiceNo").getAsString();
                if (!busMap.containsKey(serviceNo)) {
                    BusService bus = new BusService(serviceNo);
                    busMap.put(serviceNo, bus);
                    bus.addRoute(arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(),
                            arr.get(i).getAsJsonObject().get("Direction").getAsInt());
                } else {
                    busMap.get(serviceNo).addRoute(arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(),
                            arr.get(i).getAsJsonObject().get("Direction").getAsInt());
                }
            }
        }

        return busMap;
    }
}
