package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Static utility class responsible for reading and returning the contents of the given URL as a string.
 *
 */
public class URLRetriever {

    /**
     * Responsible for reading and returning the contents of the given URL as a string.
     * @param url The URL to read data from.
     * @return the contents of the URL as a string.
     */
    public static String readURLAsString(URL url) throws Exceptions {
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
            throw new Exceptions(PromptMessages.API_TIME_OUT);
        } catch (IOException ex) { throw new Exceptions(PromptMessages.API_FAIL_GENERAL);
        }
    }
}

