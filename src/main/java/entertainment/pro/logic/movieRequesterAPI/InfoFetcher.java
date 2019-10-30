package entertainment.pro.logic.movieRequesterAPI;

/**
 * Interface for client classes to be updated about a data fetch by MovieInfoFetcher.
 * Called when search requests are done.
 * As such, this listener is not called when data fetch is done to extract cast and cert information for a movie/TV show.
 */
public interface InfoFetcher {
    void fetchedJSON(String json);
    void connectionTimedOut();
}
