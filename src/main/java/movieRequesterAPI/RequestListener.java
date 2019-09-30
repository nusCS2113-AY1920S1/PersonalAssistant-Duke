package movieRequesterAPI;

import object.MovieInfoObject;

import java.util.ArrayList;

public interface RequestListener {

    void requestCompleted(ArrayList<MovieInfoObject> moviesInfo);
    void requestTimedOut();
    void requestFailed();
}