package sgtravel.model.planning;

import org.junit.jupiter.api.Test;
import sgtravel.commons.exceptions.ChronologyBeforePresentException;
import sgtravel.commons.exceptions.ChronologyInconsistentException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ItineraryTest {

    @Test
    void testCheckValidDate() {
        LocalDateTime startDatePast = LocalDateTime.of(1998, 9, 9, 9, 9);
        LocalDateTime endDatePast = LocalDateTime.of(1998, 9, 13, 9, 9);

        Itinerary itineraryPastDates = new Itinerary(startDatePast , endDatePast, "TestItinerary1");
        assertThrows(ChronologyBeforePresentException.class, itineraryPastDates::checkValidDate);

        LocalDateTime startDateInvalid = LocalDateTime.of(2020, 9, 13, 9, 9);
        LocalDateTime endDateInvalid = LocalDateTime.of(2020, 9, 9, 9, 9);

        Itinerary itineraryInvalidDates = new Itinerary(startDateInvalid , endDateInvalid, "TestItinerary2");
        assertThrows(ChronologyInconsistentException.class, itineraryInvalidDates::checkValidDate);
    }
}
