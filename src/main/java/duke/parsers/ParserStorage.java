package duke.parsers;

import duke.commons.DukeDateTimeParseException;
import duke.commons.DukeException;
import duke.commons.Message;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.Todo;

/**
 * Parser for Storage related operations.
 */
public class ParserStorage {
    /**
     * Parses a task from String format back to task.
     *
     * @param line The String description of a task.
     * @return The corresponding task object.
     */
    public static Task createTaskFromStorage(String line) throws DukeException {
        String[] taskParts = line.split("\\|");
        try {
            String type = taskParts[0].strip();
            String status = taskParts[1].strip();
            String description = taskParts[2].strip();
            Task task;
            if (type.equals("D")) {
                try {
                    task = new Deadline(description, ParserTime.parseStringToDate(taskParts[3].strip()));
                } catch (DukeDateTimeParseException e) {
                    task = new Deadline(description, taskParts[3].strip());
                }
            } else if (type.equals("E")) {
                try {
                    task = new Event(description, ParserTime.parseStringToDate(taskParts[3].strip()));
                } catch (DukeDateTimeParseException e) {
                    task = new Event(description, taskParts[3].strip());
                }
            } else {
                task = new Todo(description);
            }
            task.setDone(status.equals("true"));
            return task;
        } catch (Exception e) {
            throw new DukeException(Message.CORRUPTED_TASK);
        }
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
        }
        throw new DukeException(Message.CORRUPTED_TASK);
    }
}
