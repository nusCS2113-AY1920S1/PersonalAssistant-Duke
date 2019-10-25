package entertainment.pro.logic.movieRequesterAPI;

import java.util.ArrayList;

public interface InfoFetcherWithPreference {
    void fetchedMoviesJSONWithPreference(String json, ArrayList<Integer> genrePreference, ArrayList<Integer> genreRestriction);
    void connectionTimedOut();
}
