package entertainment.pro.logic.movieRequesterAPI;

import entertainment.pro.model.MovieInfoObject;

import java.util.ArrayList;

public interface RequestListener {

    void requestCompleted(ArrayList<MovieInfoObject> moviesInfo);
    void requestTimedOut();
    void requestFailed();
}