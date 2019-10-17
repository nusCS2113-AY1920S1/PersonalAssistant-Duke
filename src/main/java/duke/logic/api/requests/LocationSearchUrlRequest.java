package duke.logic.api.requests;

import com.google.gson.JsonElement;
import duke.commons.exceptions.DukeApiException;
import duke.commons.exceptions.DukeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import duke.commons.Messages;

import javax.annotation.processing.SupportedSourceVersion;

/**
 * URL request to OneMap API to get coordinates of location.
 */
public class LocationSearchUrlRequest extends UrlRequest {
    private static final String paramType = "searchVal";
    private static final String optionalVariables = "&returnGeom=Y&getAddrDetails=Y&pageNum=1";
    private static final String URL = "https://developers.onemap.sg/commonapi/search?";

    /**
     * Construct the URL Request.
     *
     * @param param The query
     */
    public LocationSearchUrlRequest(String param) {
        super(param.replace(" ", "+"));
    }

    /**
     * Executes the URL request to OneMap API.
     *
     * @return JSONObject The response from request
     */
    @Override
    public JsonObject execute() throws DukeApiException {
        String response;
        try {
            URL url = new URL(URL + paramType + "=" + param + optionalVariables);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = in.readLine();
            in.close();
        } catch (IOException e) {
            throw new DukeApiException(Messages.DATA_NOT_FOUND);
        }

        JsonObject result;
        try {
            assert (response != null);
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(response);
            result = root.getAsJsonObject();
        } catch (Throwable e) {
            throw new DukeApiException(Messages.DATA_NULL);
        }

        return result;
    }
}
