package duke.util;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;




public class RequestsData {

    private static Integer val = 0;

    /**
     * With reference from :https://openjdk.java.net/groups/net/httpclient/intro.html.
     * Using the nusMods V2 API : https://api.nusmods.com/v2/
     */
    public void setRequestData(String mod, Storage store) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.nusmods.com/v2/2018-2019/modules/CS1010.json"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .build();
        try {
            // TODO: Remove this after testing
            if (val == 0) {
                val++;
                return;
            }
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
            val++;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }
}
