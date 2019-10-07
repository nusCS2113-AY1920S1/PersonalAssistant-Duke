package duke.parsers;

import duke.commons.DukeDateTimeParseException;
import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.data.tasks.Deadline;
import duke.data.tasks.DoWithin;
import duke.data.tasks.Event;
import duke.data.tasks.Fixed;
import duke.data.tasks.Holiday;
import duke.data.tasks.RecurringTask;
import duke.data.tasks.Task;
import duke.data.tasks.Todo;

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
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
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
        if (deadlineDetails.length != 2 || deadlineDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (deadlineDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        try {
            return new Deadline(deadlineDetails[0].strip(),
                    ParserTimeUtil.parseStringToDate(deadlineDetails[1].strip()));
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
    protected static Event createEvent(String userInput) throws DukeException {
        String[] eventDetails = userInput.substring("event".length()).strip().split("at");
        if (eventDetails.length != 2 || eventDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (eventDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        try {
            return new Event(eventDetails[0].strip(), ParserTimeUtil.parseStringToDate(eventDetails[1].strip()));
        } catch (DukeDateTimeParseException e) {
            return new Event(eventDetails[0].strip(), eventDetails[1].strip());
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
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
        return new DoWithin(withinDetails[0].strip(), start, end);
    }

    /**
     * Parses the user input and creates a recurring task.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new recurring task.
     */
    protected static Task createRecurringTask(String userInput) throws DukeException {
        String[] taskDetails = userInput.substring("repeat".length()).strip().split("at");
        try {
            String[] dateDetails = taskDetails[1].split("every");
            if (dateDetails.length != 2 || dateDetails[1] == null) {
                throw new DukeException(MessageUtil.INVALID_FORMAT);
            }
            if (taskDetails[0].strip().isEmpty()) {
                throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
            }
            return new RecurringTask(taskDetails[0].strip(), ParserTimeUtil.parseStringToDate(dateDetails[0].strip()),
                    getIndex(dateDetails[1].strip()) + 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
    }

    /**
     * Parses the userInput and return a new Fixed constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Fixed object.
     */
    protected static Fixed createFixed(String userInput) throws  DukeException {
        String[] fixedDetails = userInput.substring("fixed".length()).strip().split("needs");
        if (fixedDetails.length != 2 || fixedDetails[1] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (fixedDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        try {
            int hour = 0;
            int min = 0;

            String[] timeDetails = fixedDetails[1].strip().split("hours");

            if (timeDetails.length == 2) {
                hour = Integer.parseInt(timeDetails[0].strip());
                min = Integer.parseInt(timeDetails[1].replaceAll("mins","").strip());
            } else if (timeDetails[0].contains("mins")) {
                min = Integer.parseInt(timeDetails[0].replaceAll("mins","").strip());
            } else {
                hour = Integer.parseInt(timeDetails[0].strip());
            }
            return new Fixed(fixedDetails[0].strip(),hour,min);
        } catch (NumberFormatException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }

    }

    /**
     * Parses the userInput and return a new Holiday constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Holiday object
     */
    public static Holiday createHoliday(String userInput) throws DukeException {
        String[] withinDetails = userInput.substring("holiday".length()).strip().split("between|and");
        if (withinDetails.length != 3 || withinDetails[1] == null || withinDetails[2] == null) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
        return new Holiday(withinDetails[0].strip(), start, end);
    }

    /**
     * Parses the userInput and return an index extracted from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The index.
     */
    protected static int getIndex(String userInput) throws DukeException {
        try {
            int index = Integer.parseInt(userInput.replaceAll("\\D+", ""));
            return index - 1;
        } catch (NumberFormatException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
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
            throw new DukeException(MessageUtil.OUT_OF_BOUNDS);
        } catch (NumberFormatException e) {
            throw new DukeException(MessageUtil.INVALID_FORMAT);
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
            throw new DukeException(MessageUtil.INVALID_FORMAT);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(MessageUtil.EMPTY_DESCRIPTION);
        }
    }
}
