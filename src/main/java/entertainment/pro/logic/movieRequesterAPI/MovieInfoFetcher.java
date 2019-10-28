package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.FailedAPINullRequest;

import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidParameterException;

/**
 * Class responsible for fetching data from the MovieDB API asynchronously.
 */
public class MovieInfoFetcher implements Runnable {
    private URL mRequestURL;
    private InfoFetcher mRequestListener;


    /**
     * Responsible for constructing fetcher with a given URL.
     *
     * @param requestURL The URL for sending the HTTP request
     * @param listener   The listener to call when the fetch completes or fails
     */
    public MovieInfoFetcher(URL requestURL, InfoFetcher listener) throws Exceptions {
        if (requestURL == null) {
            throw new Exceptions(PromptMessages.API_INVALID_REQUEST);
        }
        mRequestListener = listener;
        mRequestURL = requestURL;
    }


    /**
     * Responsible for begining the data fetch asynchronously.
     */
    @Override
    public void run() {
        try {
            String json = URLRetriever.readURLAsString(mRequestURL);
            mRequestListener.fetchedJSON(json);
            //System.out.println("passed");
        } catch (Exceptions ex) {
            // Notify the listener that the connection have timed out.
            mRequestListener.connectionTimedOut();
        }
    }
}