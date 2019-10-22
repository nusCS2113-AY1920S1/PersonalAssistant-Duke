package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.api.ApiParser;
import duke.model.events.Event;
import duke.model.locations.Venue;
import duke.model.planning.Itinerary;
import duke.model.planning.Todo;

import java.time.LocalDateTime;

/**
 * Parser for utility functions.
 */
public class ParserUtil {
    /**
     * Parses the userInput and return a new to-do constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new to-do object.
     */
    protected static Todo createTodo(String userInput) throws DukeException {
        String description = userInput.substring("todo".length()).strip();
        if (description.isEmpty()) {
            throw new DukeUnknownCommandException();
        }
        return new Todo(description);
    }

    /**
     * Parses the userInput and return a new Event constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Event object.
     */
    protected static Event createEvent(String userInput) throws DukeException {
        String[] withinDetails = userInput.substring("event".length()).strip().split("between| and");
        if (withinDetails.length == 1) {
            throw new DukeUnknownCommandException();
        }
        if (withinDetails.length != 3 || withinDetails[1] == null || withinDetails[2] == null) {
            throw new DukeException(Messages.INVALID_FORMAT);
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.EMPTY_DESCRIPTION);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
        return new Event(withinDetails[0].strip(), start, end);
    }

    /**
     * Parses the userInput and return a new Itinerary constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Itinerary object.
     */
    protected static Itinerary createRecommendation(String userInput) throws DukeException {
        String[] itineraryDetails = userInput.substring("recommend".length()).strip().split("between| and");
        if (itineraryDetails.length == 1) {
            throw new DukeUnknownCommandException();
        }
        if (itineraryDetails.length != 3 || itineraryDetails[1] == null || itineraryDetails[2] == null) {
            throw new DukeException(Messages.INVALID_FORMAT);
        }
        if (itineraryDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.EMPTY_DESCRIPTION);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[2].strip());
        Venue hotelLocation = ApiParser.getLocationSearch(itineraryDetails[0].strip());
        return new Itinerary(start, end, hotelLocation);
    }

    /**
     * Parses the userInput and return an index extracted from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    public static int getIndex(String userInput) throws DukeException {
        try {
            int index = Integer.parseInt(userInput.replaceAll("\\D+", ""));
            return index - 1;
        } catch (NumberFormatException e) {
            throw new DukeUnknownCommandException();
        }
    }

    /**
     * Parses the userInput and return an index extracted from it safely.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    protected static int getSafeIndex(String userInput) throws DukeException {
        try {
            String index = userInput.split(" ")[1].strip();
            return Integer.parseInt(index) - 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        } catch (NumberFormatException e) {
            throw new DukeUnknownCommandException();
        }
    }
}
