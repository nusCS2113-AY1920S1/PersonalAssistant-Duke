package duke.parsers;

import duke.commons.DukeDateTimeParseException;
import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.data.Location;
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
        } else if ("E".equals(type)) {
            try {
                task = new Event(description, ParserTimeUtil.parseStringToDate(taskParts[3].strip()));
            } catch (DukeDateTimeParseException e) {
                task = new Event(description, taskParts[3].strip());
            }
        } else if ("W".equals(type)) {
            LocalDateTime start = ParserTimeUtil.parseStringToDate(taskParts[3].strip());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(taskParts[4].strip());
            task = new DoWithin(description, start, end);
        } else if ("R".equals(type)) {
            task = new RecurringTask(description, ParserTimeUtil.parseStringToDate(taskParts[3].strip()),
                    Integer.parseInt(taskParts[4].strip()));
        } else if ("F".equals(type)) {
            int hour = Integer.parseInt(taskParts[3].strip());
            int min = Integer.parseInt(taskParts[4].strip());
            task = new Fixed(description, hour, min);
        }  else if ("H".equals(type)) {
            LocalDateTime start = ParserTimeUtil.parseStringToDate(taskParts[3].strip());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(taskParts[4].strip());
            String address = taskParts[5].strip();
            double longitude = Double.parseDouble(taskParts[6].strip());
            double latitude = Double.parseDouble(taskParts[7].strip());
            double distX = Double.parseDouble(taskParts[7].strip());
            double distY = Double.parseDouble(taskParts[8].strip());
            Location location = new Location(address,latitude,longitude,distX,distY);
            task = new Holiday(description, start, end, location);
        } else {
            task = new Todo(description);
        }
        task.setDone("true".equals(status));
        return task;
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
            return "E | " + task.isDone() + " | " + task.getDescription() + " | " + ((Event) task).getEvent();
        } else if (task instanceof Holiday) {
            return "H | " + task.isDone() + " | " + task.getDescription() + " | " + ((Holiday) task).getHoliday();
        } else if (task instanceof DoWithin) {
            return "W | " + task.isDone() + " | " + task.getDescription() + " | " + ((DoWithin) task).getWithin();
        } else if (task instanceof RecurringTask) {
            return ("R | " + task.isDone() + " | " + task.getDescription() + " | "
                    + ((RecurringTask) task).getStartDate() + " | " +  ((RecurringTask) task).getRepeatInterval());
        } else if (task instanceof Fixed) {
            return "F | " + task.isDone() + " | " + task.getDescription() + " | " + ((Fixed) task).getFixed();
        }
        throw new DukeException(MessageUtil.CORRUPTED_TASK);
    }
}
