package entertainment.pro.storage.utils.offlineSearch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.MovieModel;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.storage.utils.BlacklistStorage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class CurrentMoviesOffline {

    /**
     * load blacklisted movies and keywords from JSON duke.storage.
     */
    public JSONObject load() throws IOException {
        ArrayList<String> getInfo = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream("./currentMovies.json");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String defau = "";
        String line;

        while ((line = br.readLine()) != null) {
            defau += line;
        }
        br.close();
        isr.close();
        is.close();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) jsonParser.parse(defau);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}


