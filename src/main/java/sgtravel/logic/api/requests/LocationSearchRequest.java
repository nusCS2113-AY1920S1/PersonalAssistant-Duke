package sgtravel.logic.api.requests;

import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.parsers.storageparsers.PlanningStorageParser;
import sgtravel.model.locations.Venue;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles search request perform locally.
 */
public class LocationSearchRequest {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String VENUE_FILE_PATH = "/data/venues.txt";

    /**
     * Searches venue from local database.
     *
     * @param query The query.
     */
    public Venue search(String query) throws ApiException {
        logger.log(Level.INFO, query);
        Scanner s = new Scanner(getClass().getResourceAsStream(VENUE_FILE_PATH));
        String queryUppercase = query.toUpperCase();
        while (s.hasNext()) {
            try {
                Venue v = getNextVenue(s);
                if (v.getAddress().contains(queryUppercase)) {
                    return v;
                }
            } catch (ParseException e) {
                logger.log(Level.WARNING, "Resource folder has been modified.");
            }
        }
        s.close();
        throw new ApiException();
    }

    /**
     * Gets the next Venue from the Storage.
     *
     * @param s The Scanner object.
     * @return The Venue found from the Storage.
     * @throws ParseException If the Storage fails to load.
     */
    private Venue getNextVenue(Scanner s) throws ParseException {
        return PlanningStorageParser.getVenueFromStorage(s.nextLine());
    }
}
