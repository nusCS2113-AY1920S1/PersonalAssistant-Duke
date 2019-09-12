package duke.core;

import duke.command.*;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class Parser {
    private static String[] substring;

    /**
     * Parses a Task from a string array.
     *
     * @param ss The string array to be parsed.
     * @return The Command received from user.
     */
    public static Command parse(String ss) throws DukeException {
        ss = ss.trim();
        String[] command = ss.split(" ", 2);

        switch (command[0]) {
            case "list":
                return new ListCommand();
            case "done":
                try {
                    int i = Integer.parseInt(command[1]);
                    return new DoneCommand(i);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "delete":
                try {
                    int x = Integer.parseInt(command[1]);
                    return new DeleteCommand(x);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "todo":
                try {
                    Todo t = new Todo(command[1]);
                    return new AddCommand(t);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "deadline":
                try {
                    String[] temp = command[1].split(" /by ");
                    Deadline d = new Deadline(temp[0], temp[1]);
                    return new AddCommand(d);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "event":
                try {
                    String[] temp1 = command[1].split(" /at ");
                    Event z = new Event(temp1[0], temp1[1]);
                    return new AddCommand(z);
                } catch (Exception e) {
                    throw new DukeException(e.getMessage());
                }
            case "find":
                return new FindCommand(command[1]);
            case "bye":
                return new ExitCommand();
            default:
                throw new DukeException("Unrecognized user input!");
        }
    }

}
