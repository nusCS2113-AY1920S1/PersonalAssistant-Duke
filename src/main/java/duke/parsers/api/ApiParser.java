package duke.parsers.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import duke.commons.MessageUtil;
import duke.data.Location;
import duke.parsers.requests.LocationSearchUrlRequest;
import duke.commons.DukeException;

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
}
