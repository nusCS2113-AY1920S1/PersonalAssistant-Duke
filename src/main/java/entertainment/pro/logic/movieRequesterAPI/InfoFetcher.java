package entertainment.pro.logic.movieRequesterAPI;

/**
 * Interface for client classes to be updated about a fetch request by MovieTVInfoFetcher objects.
 */
public interface InfoFetcher {
    void fetchedMoviesJSON(String json);
    void connectionTimedOut();
}
