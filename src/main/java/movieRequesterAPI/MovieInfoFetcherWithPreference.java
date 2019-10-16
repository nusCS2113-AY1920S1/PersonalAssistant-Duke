package movieRequesterAPI;


import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class MovieInfoFetcherWithPreference implements Runnable
{
    private URL mRequestURL;
    private InfoFetcherWithPreference mRequestListener;
    private ArrayList<Integer> genrePreference;
    private ArrayList<Integer> genreRestriction;

    /**
     * Construct fetcher with given URL
     * @param requestURL The URL for sending the HTTP request
     * @param listener The listener to call when the fetch completes or fails
     */
    public MovieInfoFetcherWithPreference(URL requestURL, InfoFetcherWithPreference listener, ArrayList<Integer> genrePreference, ArrayList<Integer> genreRestriction) throws InvalidParameterException
    {
        if (requestURL == null){
            throw new InvalidParameterException("NULL URL passed to fetcher.");
        }
        
        mRequestListener = listener;
        mRequestURL = requestURL;
        this.genrePreference = genrePreference;
        this.genreRestriction = genreRestriction;
    }

    @Override
    /**
     * Begin the data fetch asynchronously.
     */
    public void run() 
    {
        try 
        {
            String json = URLRetriever.readURLAsString(mRequestURL);
            mRequestListener.fetchedMoviesJSONWithPreference(json, genrePreference, genreRestriction);
        } 
        catch (SocketTimeoutException ex)
        {
            // Notify the listener that a connection timed out.
            mRequestListener.connectionTimedOut();
        }      
    }
}