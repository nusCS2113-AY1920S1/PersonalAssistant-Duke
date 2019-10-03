package duke.requests;

import com.google.gson.JsonElement;
import duke.commons.DukeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * URL request to OneMap API to get coordinates of location.
 */
public class LocationSearchUrlReq extends UrlReq {
    private String paramType = "searchVal";
    private String optionalVariables = "&returnGeom=Y&getAddrDetails=Y&pageNum=1";

    /**
     * Construct the URL Request.
     * @param url The URL
     * @param param The query
     */
    public LocationSearchUrlReq(String url, String param) {
        super(url, param);
    }

    /**
     * Executes the URL request to OneMap API.
     * @return JSONObject The response from request
     */
    @Override
    public JsonObject execute() throws DukeException, IOException {
        URL url = new URL(this.url + paramType + "=" + param + optionalVariables);
        URLConnection connection = url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = in.readLine();
        in.close();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(response);
        JsonObject result = root.getAsJsonObject();

        return result;
    }
}
