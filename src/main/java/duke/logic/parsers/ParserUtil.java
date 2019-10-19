package duke.logic.parsers;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.model.events.Deadline;
import duke.model.events.DoWithin;
import duke.model.events.Event;
import duke.model.events.Todo;

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
     * Parses the userInput and return a new deadline constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new deadline object.
     */
    protected static Deadline createDeadline(String userInput) throws DukeException {
        String[] deadlineDetails = userInput.substring("deadline".length()).strip().split("by");
        if (deadlineDetails.length == 1) {
            throw new DukeUnknownCommandException();
        } else if (deadlineDetails.length != 2 || deadlineDetails[1] == null) {
            throw new DukeException(Messages.INVALID_FORMAT);
        } else if (deadlineDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.EMPTY_DESCRIPTION);
        }
        try {
            return new Deadline(deadlineDetails[0].strip(),
                    ParserTimeUtil.parseStringToDate(deadlineDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            throw new DukeException(MessagesPrompt.PROMPT_NOT_DATE);
        }
    }

    /**
     * Parses the userInput and return a new DoWithin constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new DoWithin object.
     */
    protected static DoWithin createWithin(String userInput) throws DukeException {
        String[] withinDetails = userInput.substring("within".length()).strip().split("between|and");
        if (withinDetails.length != 3 || withinDetails[1] == null || withinDetails[2] == null) {
            throw new DukeException(Messages.INVALID_FORMAT);
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.EMPTY_DESCRIPTION);
        }
        try {
            LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
            return new DoWithin(withinDetails[0].strip(), start, end);
        } catch (DukeDateTimeParseException e) {
            throw new DukeException(MessagesPrompt.PROMPT_NOT_DATE);
        }
    }

    /**
     * Parses the userInput and return a new Event constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Event object.
     */
    protected static Event createEvent(String userInput) throws DukeException {
        String[] withinDetails = userInput.substring("event".length()).strip().split("between|and");
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

    /**
     * Parses the userInput and return an date to reschedule to.
     *
     * @param userInput The userInput read by the user interface.
     * @return The date.
     */
    protected static LocalDateTime getScheduleDate(String userInput) throws DukeException {
        try {
            return ParserTimeUtil.parseStringToDate(
                    userInput.substring("reschedule".length()).strip().split("to")[1].strip());
        } catch (DukeDateTimeParseException e) {
            throw new DukeException(Messages.INVALID_FORMAT);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(Messages.EMPTY_DESCRIPTION);
        }
    }
}
