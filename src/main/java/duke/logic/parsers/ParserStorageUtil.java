package duke.logic.parsers;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.model.events.Deadline;
import duke.model.events.DoWithin;
import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.events.Todo;
import duke.model.locations.Venue;

import java.time.LocalDateTime;

/**
 * Parser for Storage related operations.
 */
public class ParserStorageUtil {
    /**
     * Parses a task from String format back to task.
     *
     * @param line The String description of a task.
     * @return The corresponding task object.
     */
    public static Task createTaskFromStorage(String line) throws DukeDateTimeParseException {
        String[] taskParts = line.split("\\|");
        String type = taskParts[0].strip();
        String status = taskParts[1].strip();
        String description = taskParts[2].strip();
        Task task;
        if ("D".equals(type)) {
            try {
                task = new Deadline(description, ParserTimeUtil.parseStringToDate(taskParts[3].strip()));
            } catch (DukeDateTimeParseException e) {
                task = new Deadline(description, taskParts[3].strip());
            }
        } else if ("W".equals(type)) {
            LocalDateTime start = ParserTimeUtil.parseStringToDate(taskParts[3].strip());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(taskParts[4].strip());
            task = new DoWithin(description, start, end);
        } else if ("E".equals(type)) {
            LocalDateTime start = ParserTimeUtil.parseStringToDate(taskParts[3].strip());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(taskParts[4].strip());
            Venue location = getLocationFromStorage(taskParts);
            task = new Event(description, start, end, location);
        } else {
            task = new Todo(description);
        }
        task.setDone("true".equals(status));
        return task;
    }

    /**
     * Parses part of a task back to a Location.
     */
    private static Venue getLocationFromStorage(String[] taskParts) {
        String address = taskParts[5].strip();
        double longitude = Double.parseDouble(taskParts[7].strip());
        double latitude = Double.parseDouble(taskParts[6].strip());
        double distX = Double.parseDouble(taskParts[7].strip());
        double distY = Double.parseDouble(taskParts[8].strip());
        return new Venue(address, latitude, longitude, distX, distY);
    }

    /**
     * Parses a task from task to String format.
     *
     * @param task The task.
     * @return The corresponding String format of the task object.
     */
    public static String toStorageString(Task task) throws DukeException {
        if (task instanceof Deadline) {
            return "D | " + task.isDone() + " | " + task.getDescription() + " | " + ((Deadline) task).getDeadline();
        } else if (task instanceof Todo) {
            return  "T | " + task.isDone() + " | " + task.getDescription();
        } else if (task instanceof Event) {
            return "E | " + task.isDone() + " | " + task.getDescription() + " | " + ((Event) task).getHoliday();
        } else if (task instanceof DoWithin) {
            return "W | " + task.isDone() + " | " + task.getDescription() + " | " + ((DoWithin) task).getWithin();
        }
        throw new DukeException(Messages.CORRUPTED_TASK);
    }
}
