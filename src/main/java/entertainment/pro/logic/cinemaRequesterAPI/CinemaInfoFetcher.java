package entertainment.pro.logic.cinemaRequesterAPI;

public interface CinemaInfoFetcher {
    void fetchedCinemasJSON(String json);
    void connectionTimedOut();
}
