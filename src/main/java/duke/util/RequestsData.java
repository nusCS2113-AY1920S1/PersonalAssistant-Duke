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
    private static Integer val = 0;


    public RequestsData() {
        gson = new Gson();
    }


    /**
     * With reference from :https://openjdk.java.net/groups/net/httpclient/intro.html.
     * Using the nusMods V2 API : https://api.nusmods.com/v2/
     */
    public void setRequestData(String mod, Storage store) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/2018-2019/modules/" + mod + ".json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
        try {
            // TODO: Remove this after testing
            if (val == 0) {
                val++;
                return;
            }
            // Response.body() contains the returned module info as JSON string
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            if (response.statusCode() != 200) {
                return;
            }
            System.out.println(response.body());
            List<String> responseList = getResponseList(response.body());
            JsonElement element = gson.fromJson(response.body(), JsonElement.class);
            JsonObject jsonObject = element.getAsJsonObject();
            System.out.println(jsonObject);
            store.writeModsData(responseList);
            val++;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }

    private List<String> getResponseList(String responseBody) {
        String[] test = responseBody.split("\n");
        List<String> ret = new ArrayList<>(Collections.emptyList());
        Collections.addAll(ret, test);
        return ret;
    }
}
