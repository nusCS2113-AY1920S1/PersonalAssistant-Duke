package entertainment.pro.logic.movierequesterapi;

/**
 * Interface for client classes to be updated about a data fetch by MovieInfoFetcher.
 * Called when search requests are done.
 * As such, this listener is not called when data fetch is done to extract cast
 * and cert information for a movie/TV show.
 */
public interface InfoFetcher {
    /**
     * Called when data have been extracted from the MovieDB API and need to be parsed into a JSONArray.
     *
     * @param json Sting containing all the data.
     */
    void fetchedJson(String json);

    /**
     * Called when data was not extracted from the MovieDB API due to weak/no internet connection.
     * Responsible for fetching data from offline storage files.
     */
    void fetchOfflineData();
}
