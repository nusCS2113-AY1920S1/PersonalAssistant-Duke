package duke.util;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class RequestsData {

    private Gson gson;

    public RequestsData() {
        gson = new Gson();
    }

    //TODO: should the requests for each query be separate?
    private HttpRequest requestModule(String mod) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/2019-2020/modules/" + mod + ".json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpRequest requestModuleList() {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/2019-2020/modulesList/.json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
    }


    /**
     * With reference from :https://openjdk.java.net/groups/net/httpclient/intro.html.
     * Using the nusMods V2 API : https://api.nusmods.com/v2/
     */
    public void getModJsonString(String mod, Storage store) {
        // Api calls only work with upper case module code
        String trimMod = mod.trim();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = requestModule(trimMod);
        try {
            // Response.body() contains the returned module info as JSON string
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            // If return status is not 200, and error request has been made
            if (response.statusCode() != 200) {
                return;
            }
            System.out.println(response.body());
            List<String> responseList = getResponseList(response.body());
            store.writeModsData(responseList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
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

    private List<String> getResponseList(String responseBody) {
        String[] test = responseBody.split("\n");
        List<String> ret = new ArrayList<>(Collections.emptyList());
        Collections.addAll(ret, test);
        return ret;
    }
}
