package duke.logic.api.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import duke.commons.exceptions.ApiException;
import duke.model.locations.Venue;

/**
 * Handles URL requests to OneMap API to get coordinates of location.
 */
public class LocationSearchUrlRequest extends UrlRequest {
    private static final String PARAM_TYPE = "searchVal";
    private static final String OPTIONAL_VARIABLES = "&returnGeom=Y&getAddrDetails=Y&pageNum=1";
    private static final String API_LINK = "https://developers.onemap.sg/commonapi/search?";

    /**
     * Constructs the URL Request.
     *
     * @param param The query.
     */
    public LocationSearchUrlRequest(String param) {
        super(param.replace(" ", "+"));
    }

    /**
     * Executes the URL request to OneMap API.
     *
     * @return JSONObject The response from OneMap API.
     * @throws ApiException If there is an issue with the request.
     */
    @Override
    public Venue execute() throws ApiException {
        String response;
        try {
            URL url = new URL(API_LINK + PARAM_TYPE + "=" + param + OPTIONAL_VARIABLES);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = in.readLine();
            in.close();
        } catch (IOException e) {
            throw new ApiException();
        }
        if (response != null) {
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(response);
            JsonObject result = root.getAsJsonObject();
            JsonArray arr = result.getAsJsonArray("results");

            if (Integer.parseInt(String.valueOf(result.getAsJsonPrimitive("found"))) > 0) {
                return new Venue(arr.get(0).getAsJsonObject().get("ADDRESS").getAsString(),
                        arr.get(0).getAsJsonObject().get("LATITUDE").getAsDouble(),
                        arr.get(0).getAsJsonObject().get("LONGITUDE").getAsDouble(),
                        arr.get(0).getAsJsonObject().get("X").getAsDouble(),
                        arr.get(0).getAsJsonObject().get("Y").getAsDouble());
            }
        }
        throw new ApiException();
    }
}
