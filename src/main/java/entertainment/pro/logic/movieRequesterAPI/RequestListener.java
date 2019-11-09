package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.model.MovieInfoObject;

import java.util.ArrayList;

/**
 * Interface responsible for listening to completed fetch requests from the MovieDB API.
 */
public interface RequestListener {

    /**
     * Called to print message that data has been successfully fetched from the MovieDB API in the UI.
     *
     * @param message String to be printed.
     */
    void requestCompleted(String message);

    /**
     * Called to print message that data was not extracted from the MovieDB API due to bad/no internet connection in the UI.
     * Also, called whenit is unable tp recache data.
     *
     * @param message String to be printed.
     */
    void requestTimedOut(String message);

    /**
     * Called when there was no data found that matches the search request.
     */
    void emptyResults();

    /**
     * Called to transmit search results data to be displayed in the UI.
     *
     * @param resultData contains all the search results data to be displayed in the UI.
     */
    void obtainedResultsData(ArrayList<MovieInfoObject> resultData);
}