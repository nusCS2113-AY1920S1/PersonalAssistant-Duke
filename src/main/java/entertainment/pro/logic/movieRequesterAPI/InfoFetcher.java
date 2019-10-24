package entertainment.pro.logic.movieRequesterAPI;

public interface InfoFetcher {
    void fetchedMoviesJSON(String json);
    void connectionTimedOut();
}
