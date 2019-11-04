package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.storage.utils.OfflineSearchStorage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static javafx.scene.input.KeyCode.R;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetrieveRequestTest {

    @Test
    public void getMovieCertFromJSONTest_valid_result() throws Exceptions {
        InputStream inputStream = OfflineSearchStorage.class.getResourceAsStream("/data/ValidTVCertFile.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        String dataFromJSON = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                dataFromJSON += line;
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            throw new Exceptions(PromptMessages.IO_EXCEPTION_IN_OFFLINE);
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        JSONArray searchData = new JSONArray();
        try {
            jsonObject = (JSONObject) jsonParser.parse(dataFromJSON);
            searchData = (JSONArray) jsonObject.get("results");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String cert = RetrieveRequest.getMovieCertFromJSON(searchData);
        String expected = "\"R\"";
        assertEquals(expected, cert);

    }

}
