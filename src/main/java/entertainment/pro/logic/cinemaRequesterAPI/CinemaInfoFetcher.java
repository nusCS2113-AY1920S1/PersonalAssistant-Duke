package entertainment.pro.logic.cinemaRequesterAPI;

/**
 * Interface for client classes to be notified about a fetch request by CinemaInfo objects.
 */
public interface CinemaInfoFetcher {
    void fetchedCinemasJson(String json);

    void connectionTimedOut();
}
