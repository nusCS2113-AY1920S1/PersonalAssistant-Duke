package task;

import exception.DukeException;
import storage.Constants;
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
            switch (splitStr[Constants.TYPE]) {
            case "T":
                this.add(new Todo(splitStr[Constants.ISDONE], splitStr[Constants.DESCRIPTION]));
                break;
            case "E":
                this.add(new Event(splitStr[Constants.ISDONE], splitStr[Constants.DESCRIPTION],
                        splitStr[Constants.TIMESTART], splitStr[Constants.TIMEEND]));
                break;
            case "D":
                this.add(new Deadline(splitStr[Constants.ISDONE],
                        splitStr[Constants.DESCRIPTION], splitStr[Constants.TIME]));
                break;
            case "F":
                this.add(new FixedDuration(splitStr[Constants.ISDONE],
                        splitStr[Constants.DESCRIPTION], splitStr[Constants.NEEDS]));
                break;
            case "R":
                this.add(new Recurring(splitStr[Constants.ISDONE], splitStr[Constants.DESCRIPTION],
                        splitStr[Constants.TIME], splitStr[4], splitStr[5]));
                break;
            case "DA":
                this.add(new DoAfter(splitStr[Constants.ISDONE], splitStr[Constants.DESCRIPTION],
                        splitStr[Constants.TIME]));
                break;
            case "DW":
                this.add(new DoWithinPeriod(splitStr[Constants.ISDONE], splitStr[Constants.DESCRIPTION],
                        splitStr[Constants.TIMESTART], splitStr[Constants.TIMEEND]));
                break;
            default:
                throw new DukeException("File format incorrect");
            }
        }
    }

    public TaskList() {
        super();
    }
}
