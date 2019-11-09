package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.parsers.commands.SearchCommand;
import entertainment.pro.ui.MovieHandler;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for fetching data from the MovieDB API asynchronously.
 */
public class MovieInfoFetcher implements Runnable {
    private URL movieRequestUrl;
    private InfoFetcher movieRequestListener;
    private static final Logger logger = Logger.getLogger(MovieInfoFetcher.class.getName());

    /**
     * Responsible for constructing fetcher with a given URL.
     *
     * @param requestUrl The URL for sending the HTTP request
     * @param listener   The listener to call when the fetch completes or fails
     */
    public MovieInfoFetcher(URL requestUrl, InfoFetcher listener) throws Exceptions {
        movieRequestListener = listener;
        movieRequestUrl = requestUrl;
        if (requestUrl == null) {
            logger.log(Level.WARNING, PromptMessages.NULL_URL);
            throw new Exceptions(PromptMessages.NULL_URL);
        }
    }


    /**
     * Responsible for beginning the data fetch asynchronously.
     */
    @Override
    public void run() {
        String json = "";
        try {
            json = URLRetriever.readURLAsString(movieRequestUrl);
            movieRequestListener.fetchedJSON(json);
            logger.log(Level.INFO, PromptMessages.DATA_EXTRACT_FROM_API_SUCCESS);
        } catch (NullPointerException | Exceptions e) {
            logger.log(Level.INFO, PromptMessages.DATA_EXTRACT_FROM_OFFLINE_NEEDED);
            movieRequestListener.fetchOfflineData();
            logger.log(Level.INFO, PromptMessages.DATA_EXTRACT_FROM_OFFLINE_SUCCESS);
        }
    }
}