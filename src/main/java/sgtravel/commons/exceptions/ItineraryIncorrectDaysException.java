package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Displays an error when the user enters the incorrect number of agendas in the newItinerary command.
 */
public class ItineraryIncorrectDaysException extends DukeException {
    public ItineraryIncorrectDaysException() {
        super(Messages.ERROR_ITINERARY_INCORRECT_DAYS);
    }
}
