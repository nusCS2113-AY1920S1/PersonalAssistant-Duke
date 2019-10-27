package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.model.MovieInfoObject;

import java.util.ArrayList;

/**
 * Interface responsible for listening to completed fetch requests from the MovieDB API.
 */
public interface RequestListener {

    /**
     * Called when data has been successfully fetched from the MovieDB API.
     * @param moviesInfo List of either movie or TV items extracted from the API
     */
    void requestCompleted(ArrayList<MovieInfoObject> moviesInfo);

    /**
     * Called when data has not been extracted from the MovieDB API due to bad internet connection.
     */
    void requestTimedOut();

    /**
     * Called when data has not been extracted from the MovieDB API due to no internet connection.
     */
    void requestFailed();
}