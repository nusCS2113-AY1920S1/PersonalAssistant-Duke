package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.model.MovieInfoObject;

import java.util.ArrayList;

/**
 * Interface responsible for listening to completed fetch requests from the MovieDB API.
 */
public interface RequestListener {

    /**
     * Called when data has been successfully fetched from the MovieDB API.
     */
    void requestCompleted();

    /**
     * Called when data has not been extracted from the MovieDB API due to bad/no internet connection.
     */
    void requestTimedOut();

    /**
     * Called to transmit search results data to be displayed in the UI.
     * @param resultData contains all the search results data to be displayed in the UI.
     */
    void obtainedResultsData(ArrayList<MovieInfoObject> resultData);
}