package duke.parsers.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import duke.commons.MessageUtil;
import duke.data.BusStop;
import duke.data.Location;
import duke.parsers.requests.LocationSearchUrlRequest;
import duke.commons.DukeException;
import duke.parsers.requests.DataMallHttpRequest;

import java.io.IOException;
import java.util.ArrayList;

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
    public static Location getLocationSearch(String param) throws DukeException {
        LocationSearchUrlRequest req = new LocationSearchUrlRequest("https://developers.onemap.sg/commonapi/search?",
                param);
        JsonObject jsonRes = req.execute();
        JsonArray arr = jsonRes.getAsJsonArray("results");
        if (isFound(jsonRes)) {
            return new Location(arr.get(0).getAsJsonObject().get("ADDRESS").getAsString(),
                    arr.get(0).getAsJsonObject().get("LATITUDE").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("LONGITUDE").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("X").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("Y").getAsDouble());
        }
        throw new DukeException(MessageUtil.DATA_NOT_FOUND);
        /*
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(String.valueOf(jsonRes.getAsJsonPrimitive("found")))
                ; i++) {
            result.add(arr.get(i).getAsJsonObject().get("SEARCHVAL").getAsString() + " ("
                    + arr.get(i).getAsJsonObject().get("LATITUDE").getAsString() + " : "
                    + arr.get(i).getAsJsonObject().get("LONGITUDE").getAsString() + ")");
        }
        return result;
         */
    }

    private static boolean isFound(JsonObject jsonRes) {
        return Integer.parseInt(String.valueOf(jsonRes.getAsJsonPrimitive("found"))) > 0;
    }

    /**
     * Return all bus stop in Singapore.
     *
     * @return List of Bus Stop
     */
    public static ArrayList<BusStop> getBusStop() throws DukeException, IOException {
        String path = "BusStops";

        DataMallHttpRequest req = new DataMallHttpRequest("Bus", path);
        JsonObject jsonRes = req.execute();
        ArrayList<BusStop> allBus = new ArrayList<>();
        JsonArray arr = jsonRes.getAsJsonArray("value");
        for (int i = 0; i < arr.size(); i++) {
            BusStop busstop = new BusStop(
                    arr.get(i).getAsJsonObject().get("BusStopCode").getAsString(),
                    arr.get(i).getAsJsonObject().get("Description").getAsString(),
                    arr.get(i).getAsJsonObject().get("RoadName").getAsString(),
                    arr.get(i).getAsJsonObject().get("Latitude").getAsDouble(),
                    arr.get(i).getAsJsonObject().get("Longitude").getAsDouble());
            allBus.add(busstop);
        }
        return allBus;
    }
}
