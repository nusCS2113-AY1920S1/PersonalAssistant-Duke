package duke.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.http.HttpResponse;

public class JsonWrapper {

    private Gson gson;
    private final String listFile = "data/modsListData.json";
    private final String listDetailedFile = "data/modsDetailedList.json";

    public JsonWrapper() {
        gson = new Gson();
    }

    private void readJson(HttpResponse<String> response) {
        try {
            JsonElement element = gson.fromJson(response.body(), JsonElement.class);
            JsonObject jsonObject = element.getAsJsonObject();
            System.out.println(jsonObject);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
