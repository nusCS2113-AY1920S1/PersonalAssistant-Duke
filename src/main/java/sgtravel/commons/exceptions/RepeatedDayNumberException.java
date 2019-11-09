package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Displays an error when the user enters 2 of the same day numbers in the newItinerary command.
 */
public class RepeatedDayNumberException extends ParseException {
    public RepeatedDayNumberException() {
        super(Messages.ERROR_ITINERARY_REPEATED_DAYS);
    }
}
