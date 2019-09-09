package duke.parsers;

import duke.commons.DukeDateTimeParseException;
import duke.commons.DukeException;
import duke.commons.Message;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Todo;

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
    static Todo createTodo(String userInput) throws DukeException {
        String description = userInput.substring("todo".length()).strip();
        if (description.isEmpty()) {
            throw new DukeException(Message.EMPTY_DESCRIPTION);
        }
        return new Todo(description);
    }

    /**
     * Parses the userInput and return a new deadline constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new deadline object.
     */
    static Deadline createDeadline(String userInput) throws DukeException {
        String[] deadlineDetails = userInput.substring("deadline".length()).strip().split("/by");
        if (deadlineDetails.length != 2 || deadlineDetails[1] == null) {
            throw new DukeException(Message.INVALID_FORMAT);
        }
        if (deadlineDetails[0].strip().isEmpty()) {
            throw new DukeException(Message.EMPTY_DESCRIPTION);
        }
        try {
            return new Deadline(deadlineDetails[0].strip(), ParserTime.parseStringToDate(deadlineDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            return new Deadline(deadlineDetails[0].strip(), deadlineDetails[1].strip());
        }
    }

    /**
     * Parses the userInput and return a new event constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new event object.
     */
    static Event createEvent(String userInput) throws DukeException {
        String[] eventDetails = userInput.substring("event".length()).strip().split("/at");
        if (eventDetails.length != 2 || eventDetails[1] == null) {
            throw new DukeException(Message.INVALID_FORMAT);
        }
        if (eventDetails[0].strip().isEmpty()) {
            throw new DukeException(Message.EMPTY_DESCRIPTION);
        }
        try {
            return new Event(eventDetails[0].strip(), ParserTime.parseStringToDate(eventDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            return new Event(eventDetails[0].strip(), eventDetails[1].strip());
        }
    }

    /**
     * Parses the userInput and return an index extracted from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    static int getIndex(String userInput) throws DukeException {
        try {
            int index = Integer.parseInt(userInput.replaceAll("\\D+", ""));
            return index - 1;
        } catch (NumberFormatException e) {
            throw new DukeException(Message.INVALID_FORMAT);
        }
    }
}
