package duke.parsers.requests;

import com.google.gson.JsonElement;
import duke.commons.DukeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import duke.commons.MessageUtil;

/**
 * URL request to OneMap API to get coordinates of location.
 */
public class LocationSearchUrlRequest extends UrlRequest {
    private static final String paramType = "searchVal";
    private static final String optionalVariables = "&returnGeom=Y&getAddrDetails=Y&pageNum=1";

    /**
     * Construct the URL Request.
     *
     * @param url The URL
     * @param param The query
     */
    public LocationSearchUrlRequest(String url, String param) {
        super(url, param);
    }

    /**
     * Executes the URL request to OneMap API.
     *
     * @return JSONObject The response from request
     */
    @Override
    public JsonObject execute() throws DukeException {
        String response;
        try {
            URL url = new URL(this.url + paramType + "=" + param + optionalVariables);
            URLConnection connection = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = in.readLine();
            in.close();
        } catch (IOException e) {
            throw new DukeException(MessageUtil.DATA_NOT_FOUND);
        }

        //TODO: if response == null/api call fail, do some handling
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(response);
        JsonObject result = root.getAsJsonObject();

        return result;
    }
}
