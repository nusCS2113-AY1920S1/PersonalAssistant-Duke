package movieRequesterAPI;

import object.MovieInfoObject;

import java.util.ArrayList;

public interface InfoFetcher {
    void fetchedMoviesJSON(String json);
    void connectionTimedOut();
}
