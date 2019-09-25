package movieRequesterAPI;

public interface InfoFetcher {
    void fetchedMoviesJSON(String json);
    void connectionTimedOut();
}
