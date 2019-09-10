package task;

import dukeException.DukeException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

import java.util.ArrayList;

/**
 * Contains task list
 */
public class TaskList extends ArrayList<Task> {
    public TaskList(ArrayList<String> loader) throws DukeException {
        for (String line : loader) {
            String[] splitStr = line.split(" \\| ");
            switch (splitStr[0]) {
                case "T":
                    this.add(new Todo(splitStr[1], splitStr[2]));
                    break;
                case "E":
                    this.add(new Event(splitStr[1], splitStr[2], splitStr[3]));
                    break;
                case "D":
                    this.add(new Deadline(splitStr[1], splitStr[2], splitStr[3]));
                    break;
                default:
                    throw new DukeException("File format incorrect");
            }
        }
    }

    public TaskList() {
    }
}
