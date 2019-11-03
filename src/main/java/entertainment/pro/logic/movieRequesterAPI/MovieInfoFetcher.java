package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;

import java.net.URL;

/**
 * Class responsible for fetching data from the MovieDB API asynchronously.
 */
public class MovieInfoFetcher implements Runnable {
    private URL movieRequestUrl;
    private InfoFetcher movieRequestListener;

    /**
     * Responsible for constructing fetcher with a given URL.
     *
     * @param requestUrl The URL for sending the HTTP request
     * @param listener   The listener to call when the fetch completes or fails
     */
    public MovieInfoFetcher(URL requestUrl, InfoFetcher listener) throws Exceptions {
        if (requestUrl == null) {
            throw new Exceptions(PromptMessages.API_INVALID_REQUEST);
        }
        movieRequestListener = listener;
        movieRequestUrl = requestUrl;
    }


    /**
     * Responsible for begining the data fetch asynchronously.
     */
    @Override
    public void run() {
        String json = "";
        try {
            json = URLRetriever.readURLAsString(movieRequestUrl);
            movieRequestListener.fetchedJSON(json);
            //System.out.println("passed");
        } catch (Exceptions ex) {
            movieRequestListener.fetchOfflineData();
            }
        }
    }