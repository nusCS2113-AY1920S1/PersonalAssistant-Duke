package movieRequesterAPI;


import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class MovieInfoFetcherWithGenre implements Runnable
{
    private URL mRequestURL;
    private InfoFetcherWithGenre mRequestListener;
    private ArrayList<Integer> genreID;

    /**
     * Construct fetcher with given URL
     * @param requestURL The URL for sending the HTTP request
     * @param listener The listener to call when the fetch completes or fails
     */
    public MovieInfoFetcherWithGenre(URL requestURL, InfoFetcherWithGenre listener, ArrayList<Integer> genreID) throws InvalidParameterException
    {
        if (requestURL == null){
            throw new InvalidParameterException("NULL URL passed to fetcher.");
        }
        
        mRequestListener = listener;
        mRequestURL = requestURL;
        this.genreID = genreID;
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
            mRequestListener.fetchedMoviesJSONWithGenre(json, genreID);
        } 
        catch (SocketTimeoutException ex)
        {
            // Notify the listener that a connection timed out.
            mRequestListener.connectionTimedOut();
        }      
    }
}