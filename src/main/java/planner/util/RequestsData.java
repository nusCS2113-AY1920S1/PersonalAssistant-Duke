package planner.util;


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

import planner.exceptions.planner.ModBadRequestStatus;

public class RequestsData {

    public RequestsData() {

    }

    /**
     * Request builder for API call summary module data.
     * @param mod Module of interest to be queried.
     * @return HttpRequest formatted with the nusMods API call.
     */
    public HttpRequest requestModule(String mod) {
        String upperMod = mod.trim().toUpperCase();
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/2019-2020/modules/" + upperMod + ".json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
    }

    /**
     * Request builder for API call summary module data.
     * @param academicYear Academic year of interest
     * @return HttpRequest formatted with the nusMods API call.
     */
    public HttpRequest requestModuleList(String academicYear) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/" + academicYear + "/moduleList.json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
    }

    /**
     * Request builder for API call detailed module data.
     * @param academicYear Academic year of interest
     * @return HttpRequest formatted with the nusMods API call.
     */
    public HttpRequest requestModuleListDetailed(String academicYear) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/" + academicYear + "/moduleInfo.json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
    }

    /**
     * HttpRequest with reference from :https://openjdk.java.net/groups/net/httpclient/intro.html.
     * Using the nusMods V2 API : https://api.nusmods.com/v2/
     * Stores requests made into *.json files for further processing
     */
    public void storeModData(HttpRequest request, Storage store) throws ModBadRequestStatus {
        // Api calls only work with upper case module code
        HttpClient client = HttpClient.newHttpClient();
        try {
            // Response.body() contains the returned module info as JSON string
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            // If return status is not 200, and error request has been made
            if (response.statusCode() != 200) {
                throw new ModBadRequestStatus();
            }
            List<String> responseList = getResponseList(response.body());
            store.writeModsData(responseList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            System.out.println(Arrays.toString(ie.getStackTrace()));
        }
    }

    /**
     * Formats a string to list of strings based on new line character.
     * @param responseBody String containing response from HttpRequest.
     * @return A list of string, separated by new line characters.
     */
    public List<String> getResponseList(String responseBody) {
        String[] test = responseBody.split("\n");
        List<String> ret = new ArrayList<>(Collections.emptyList());
        Collections.addAll(ret, test);
        return ret;
    }
}
