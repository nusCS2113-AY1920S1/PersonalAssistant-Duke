package entertainment.pro.logic.movieRequesterAPI;


import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidParameterException;

public class MovieInfoFetcher implements Runnable {
    private URL mRequestURL;
    private InfoFetcher mRequestListener;

    /**
     * Construct fetcher with given URL
     *
     * @param requestURL The URL for sending the HTTP request
     * @param listener   The listener to call when the fetch completes or fails
     */
    public MovieInfoFetcher(URL requestURL, InfoFetcher listener) throws InvalidParameterException {
        if (requestURL == null) {
            throw new InvalidParameterException("NULL URL passed to fetcher.");
        }

        mRequestListener = listener;
        mRequestURL = requestURL;
    }

    @Override
    /**
     * Begin the data fetch asynchronously.
     */
    public void run() {
        try {
            String json = URLRetriever.readURLAsString(mRequestURL);
            mRequestListener.fetchedMoviesJSON(json);
            //System.out.println("passed");
        } catch (SocketTimeoutException ex) {
            // Notify the listener that a connection timed out.
            mRequestListener.connectionTimedOut();
        }
    }
}