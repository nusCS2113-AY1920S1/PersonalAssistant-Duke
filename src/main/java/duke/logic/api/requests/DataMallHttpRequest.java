package duke.logic.api.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import duke.commons.exceptions.DukeApiException;
import duke.commons.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class representing Data Mall URL request.
 */
public class DataMallHttpRequest extends HttpRequest {
    private static final String URL = "http://datamall2.mytransport.sg/ltaodataservice/";

    public DataMallHttpRequest(String reqType, String path, String param) {
        super(reqType, URL + path, param);
    }

    @Override
    public JsonObject execute() throws DukeApiException {
        String response;
        try {
            URL url = new URL(this.url + "?$skip=" + this.param);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");
            con.setRequestProperty("AccountKey", "LuhGHYG6Tmu5TdcJm1g8nQ==");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
