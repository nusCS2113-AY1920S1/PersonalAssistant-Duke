package duke.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import duke.requests.LocationSearchUrlReq;
import duke.commons.DukeException;
import javafx.util.Pair;

/**
 * Class to handle all API requests.
 */
public class ApiParser {

    /**
     * Return names and coordinates of location search.
     * @param param The query
     * @return result The locations found
     */
    public static Pair<Double, Double> getLocationSearch(String param) throws DukeException {
        LocationSearchUrlReq req = new LocationSearchUrlReq("https://developers.onemap.sg/commonapi/search?",
                param);
        JsonObject jsonRes = req.execute();
        JsonArray arr = jsonRes.getAsJsonArray("results");

        if (Integer.parseInt(String.valueOf(jsonRes.getAsJsonPrimitive("found"))) > 0) {
            return new Pair<>(arr.get(0).getAsJsonObject().get("LATITUDE").getAsDouble(),
                    arr.get(0).getAsJsonObject().get("LONGITUDE").getAsDouble());
        }

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
        return new Pair<>(0.0, 0.0);
    }
}
