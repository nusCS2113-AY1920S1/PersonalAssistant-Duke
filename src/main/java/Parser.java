import command.*;
import exception.DukeException;
import task.Deadline;
import task.Recurring;
import task.Todo;
import task.Event;

import java.util.Arrays;

/**
 * Creates a Command object after extracting information needed
 * @author Zhang Yue Han
 */
public class Parser {

    public static Command parse(String input) {
        try {
            String[] taskInfo = input.split(" ", 2);
            if (taskInfo[0].equals("bye")) {
                //create an ExitCommand
                return new ExitCommand();
            } else if (taskInfo[0].equals("done")) {
                if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) throw new DukeException(DukeException.ErrorType.INDEX_MISSING);
                //create a DoneCommand object
                return new DoneCommand(Integer.parseInt(taskInfo[1]));
            } else if (taskInfo[0].equals("delete")) {
                if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) throw new DukeException(DukeException.ErrorType.INDEX_MISSING);
                //create a DeleteCommand
                return new DeleteCommand(Integer.parseInt(taskInfo[1]));
            } else if (taskInfo[0].equals("find")) {
                if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) throw new DukeException(DukeException.ErrorType.SEARCHPHRASE_MISSING);
                //create a FindCommand
                return new FindCommand(taskInfo[1]);
            } else if (taskInfo[0].equals("list")) {
                return new ListCommand();
            } else {
                if (taskInfo[0].equals("todo")) {
                    //parse and throw into AddCommand
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) throw new DukeException(DukeException.ErrorType.FORMAT_TODO);
                    Todo t = new Todo(taskInfo[1]);
                    return new AddCommand(t);
                } else if (taskInfo[0].equals("deadline")) {
                    //parse date
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                    String[] dateInfo = parseDate("deadline", taskInfo);
                    if ((Arrays.toString(dateInfo).equals("[null]")  || (dateInfo.length < 2))) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                    Date d = new Date();
                    dateInfo[1] = d.convertDate(dateInfo[1]);
                    if (dateInfo[1].equals("[null]")) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                    //create deadline object
                    Deadline t = new Deadline(dateInfo[0], dateInfo[1]);
                    return new AddCommand(t);
                } else if (taskInfo[0].equals("event")) {
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) { throw new DukeException(DukeException.ErrorType.FORMAT_EVENT); }
                    String[] dateInfo = parseDate("event", taskInfo);
                    if ((Arrays.toString(dateInfo).equals("[null]") || (dateInfo.length < 2))) { throw new DukeException(DukeException.ErrorType.FORMAT_EVENT); }
                    Date d = new Date();
                    dateInfo[1] = d.convertDate(dateInfo[1]);
                    if (dateInfo[1].equals("[null]")) { throw new DukeException(DukeException.ErrorType.FORMAT_EVENT); }
                    //create event object
                    Event t = new Event(dateInfo[0], dateInfo[1]);
                    return new AddCommand(t);
                } else if (taskInfo[0].equals("recurring")) {
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) {
                        throw new DukeException(DukeException.ErrorType.FORMAT_RECURRING);
                    }
                    String[] dateInfo = parseDate("recurring", taskInfo);
                    if ((Arrays.toString(dateInfo).equals("[null]") || (dateInfo.length < 2))) {
                        throw new DukeException(DukeException.ErrorType.FORMAT_RECURRING);
                    }
                    Date d = new Date();
                    dateInfo[1] = d.convertDate(dateInfo[1]);
                    if (!dateInfo[1].equals("null")) {
                        throw new DukeException(DukeException.ErrorType.FORMAT_RECURRING_DATE);
                    }
                    //create event object
                    Recurring t = new Recurring(dateInfo[0], dateInfo[1]);
                    return new AddCommand(t);
                }
                else {
                    try {
                        throw new DukeException(DukeException.ErrorType.COMMAND_INVALID);
                    } catch (DukeException e){
                        e.showError();
                        return new BadCommand();
                    }
                }
            }
        } catch (DukeException e) {
            e.showError();
            return new BadCommand();
        }
    }

    public static String[] parseDate(String type, String[] taskInfo) {
        String[] dateInfo = { "null" };

        if (type.equals("deadline")) {
            dateInfo = taskInfo[1].split("/by ");
            //tell AddCommand to go add itself
        } else if (type.equals("event")) {
            dateInfo = taskInfo[1].split("/at ");
            //tell AddCommand to go add itself
        } else if (type.equals("recurring")) {
            dateInfo = taskInfo[1].split("/every ");
            //tell AddCommand to go add itself
        }
        return dateInfo;
    }
}