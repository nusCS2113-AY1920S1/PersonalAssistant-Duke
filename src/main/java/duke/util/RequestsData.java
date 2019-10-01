package duke.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;

public class RequestsData {

    /**
     * With reference from :https://openjdk.java.net/groups/net/httpclient/intro.html.
     * Using the nusMods V2 API : https://api.nusmods.com/v2/
     */
    public void setRequest(String mod) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/2018-2019/modules/CS1010.json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }
}
