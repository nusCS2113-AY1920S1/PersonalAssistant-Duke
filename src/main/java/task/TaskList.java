package task;

import exception.DukeException;

import java.util.ArrayList;

/**
 * Contains task list.
 */
public class TaskList extends ArrayList<Task> {
    /**
     * Create tasklist from text file.
     * @param loader strings from text file containing task info
     * @throws DukeException if file format incorrect
     */
    public TaskList(ArrayList<String> loader) throws DukeException {
        for (String line : loader) {
            String[] splitStr = line.split(" \\| ");
            switch (splitStr[0]) {
            case "T":
                this.add(new Todo(splitStr[1], splitStr[2], splitStr[3]));
                break;
            case "E":
                this.add(new Event(splitStr[1], splitStr[2], splitStr[3], splitStr[4], splitStr[5]));
                break;
            case "D":
                this.add(new Deadline(splitStr[1], splitStr[2], splitStr[3], splitStr[4]));
                break;
            default:
                throw new DukeException("File format incorrect");
            }
        }
    }

    public TaskList() {
    }
}
