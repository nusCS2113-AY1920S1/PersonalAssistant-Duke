package duke.util;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * HttpRequest builder for specified information for a single module.
     * @return HttpRequest configured to link to NusMods API V2.
     */
    public HttpRequest modsRequestBuilder(String mod) {
        // Api calls only work with upper case module code
        String upperMod = mod.toUpperCase();
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/2018-2019/modules/" + upperMod + ".json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
    }

    /**
     * HttpRequest builder for summary list of all modules.
     * @return HttpRequest configured to link to NusMods API V2.
     */
    public HttpRequest modsRequestSummaryBuilder() {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/2019-2020/moduleList.json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
    }


    /**
     * With reference from :https://openjdk.java.net/groups/net/httpclient/intro.html.
     * Using the nusMods V2 API : https://api.nusmods.com/v2/
     * Stores requests made into *.json files for further processing
     */
    public void getModJsonString(Storage store, HttpRequest request) {
        HttpClient client = HttpClient.newHttpClient();
        try {
            // Response.body() contains the returned module info as JSON string
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            // If return status is not 200, and error request has been made
            if (response.statusCode() != 200) {
                return;
            }
            System.out.println(response.statusCode());
            List<String> responseList = getResponseList(response.body());
            store.writeModsData(responseList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            System.out.println(Arrays.toString(ie.getStackTrace()));
        }
    }

    private List<String> getResponseList(String responseBody) {
        String[] test = responseBody.split("\n");
        List<String> ret = new ArrayList<>(Collections.emptyList());
        Collections.addAll(ret, test);
        return ret;
    }

    //TODO: Use this after file writing
    private void jsonParse(HttpResponse<String> response) {
        try {
            JsonElement element = gson.fromJson(response.body(), JsonElement.class);
            JsonObject jsonObject = element.getAsJsonObject();
            System.out.println(jsonObject);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
