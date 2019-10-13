package movieRequesterAPI;

import java.util.ArrayList;

public interface InfoFetcherWithGenre {
    void fetchedMoviesJSONWithGenre(String json, ArrayList<Integer> genreID);
    void connectionTimedOut();
}
