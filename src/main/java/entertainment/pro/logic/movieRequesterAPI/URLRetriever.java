package entertainment.pro.logic.movieRequesterAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class URLRetriever {

    public static String readURLAsString(URL url) throws SocketTimeoutException {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(20 * 1000);

            int statusCode = connection.getResponseCode();
            String line = null;
            StringBuilder dataStr = null;

            // Status OK - get the data from the stream
            if (statusCode == 200) {
                dataStr = new StringBuilder();
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                line = input.readLine();
                while (line != null) {
                    dataStr.append(line);
                    line = input.readLine();
                }

                return dataStr.toString();
            }

            return null;
        } catch (SocketTimeoutException ex) {
            throw new SocketTimeoutException();
        } catch (IOException ex) {
            return null;
        }
    }
}

