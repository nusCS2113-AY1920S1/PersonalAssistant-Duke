package sgtravel.logic.api.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sgtravel.commons.exceptions.ApiException;
import sgtravel.model.locations.Venue;

/**
 * Handles Search URL requests to OneMap API.
 */
public class LocationSearchUrlRequest extends UrlRequest {
    private static final String PARAM_TYPE = "searchVal";
    private static final String OPTIONAL_VARIABLES = "&returnGeom=Y&getAddrDetails=Y&pageNum=1";
    private static final String API_LINK = "https://developers.onemap.sg/commonapi/search?";
    private final LocationSearchRequest localSearch = new LocationSearchRequest();

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
     * @return The Venue object created from the request result.
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
            return localSearch.search(param);
        }
        if (response != null) {
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(response);
            JsonObject results = root.getAsJsonObject();
            JsonArray arr = results.getAsJsonArray("results");

            String numFoundString = String.valueOf(results.getAsJsonPrimitive("found"));
            int numFound = Integer.parseInt(numFoundString);


            if (numFound > 0) {
                JsonObject firstResult = arr.get(0).getAsJsonObject();
                return new Venue(firstResult.get("ADDRESS").getAsString(), firstResult.get("LATITUDE").getAsDouble(),
                        firstResult.get("LONGITUDE").getAsDouble(), firstResult.get("X").getAsDouble(),
                        firstResult.get("Y").getAsDouble());
            }
        }
        throw new ApiException();
    }
}
