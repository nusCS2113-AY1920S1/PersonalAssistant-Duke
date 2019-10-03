package duke.api;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import duke.requests.LocationSearchUrlReq;
import duke.commons.DukeException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to handle all API requests.
 */
public class ApiParser {

    /**
     * Return names and coordinates of location search.
     * @param param The query
     * @return result The locations found
     */
    public static ArrayList<String> getLocationSearch(String param) throws IOException, DukeException {
        ArrayList<String> result = new ArrayList<>();
        LocationSearchUrlReq req = new LocationSearchUrlReq("https://developers.onemap.sg/commonapi/search?",
                param);
        JsonObject jsonRes = req.execute();

        JsonArray arr = jsonRes.getAsJsonArray("results");
        for (int i = 0; i < Integer.valueOf(String.valueOf(jsonRes.getAsJsonPrimitive("found")))
                && i < 5; i++) {
            result.add(arr.get(i).getAsJsonObject().get("SEARCHVAL").getAsString() + " ("
                + arr.get(i).getAsJsonObject().get("LATITUDE").getAsString() + " : "
                + arr.get(i).getAsJsonObject().get("LONGITUDE").getAsString() + ")");
        }

        return result;
    }
}
