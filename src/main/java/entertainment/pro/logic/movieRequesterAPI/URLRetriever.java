package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.FailedAPIException;
import entertainment.pro.ui.MovieHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Static utility class responsible for reading and returning the contents of the given URL as a string.
 *
 */
public class URLRetriever {
    private static final String TO_SPECIFY_GET = "GET";
    private static final int SET_REQUEST_TIME = 20 * 1000;
    private static final int STATUS_OK = 200;
    private static final Logger logger = Logger.getLogger(URLRetriever.class.getName());

    /**
     * Responsible for reading and returning the contents of the given URL as a string.
     * @param url The URL to read data from.
     * @return the contents of the URL as a string.
     */
    public static String readURLAsString(URL url) throws Exceptions {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(TO_SPECIFY_GET);
            connection.setReadTimeout(SET_REQUEST_TIME);
            int statusCode = connection.getResponseCode();
            String line = null;
            StringBuilder dataStr = null;
            // Status OK - get the data from the stream
            if (statusCode == STATUS_OK) {
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
            logger.log(Level.SEVERE, PromptMessages.SOCKET_TIMEOUT_URL);
            return null;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, PromptMessages.IO_ERROR_URL);
            return null;
        }
    }
}

