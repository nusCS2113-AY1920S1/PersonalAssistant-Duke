package duke.logic.api.requests;

import com.google.gson.JsonElement;
import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
     * @throws ApiNullRequestException If the request gives no valid result.
     * @throws ApiTimeoutException If the request times out.
     */
    @Override
    public JsonObject execute() throws ApiNullRequestException, ApiTimeoutException  {
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
            throw new ApiTimeoutException();
        }

        JsonObject result;
        if (response != null) {
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(response);
            result = root.getAsJsonObject();
            return result;
        }

        throw new ApiNullRequestException();
    }
}
