package duke.parsers.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import duke.commons.DukeException;
import duke.commons.MessageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataMallHttpRequest extends HttpRequest {
    public DataMallHttpRequest(String reqType, String path, String param) {
        super(reqType, "http://datamall2.mytransport.sg/ltaodataservice/" + path, param);
    }

    @Override
    public JsonObject execute() throws DukeException, IOException {
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
            throw new DukeException(MessageUtil.DATA_NOT_FOUND);
        }

        //TODO: if response == null/api call fail, do some handling
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(response);
        JsonObject result = root.getAsJsonObject();

        return result;
    }
}
