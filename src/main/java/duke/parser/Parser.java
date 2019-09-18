package duke.parser;

import duke.Duke;
import duke.command.*;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.DoWithinPeriodTasks;
import duke.task.Event;
import duke.task.Todo;

/**
 * Represents a parser used to parse the input String from the user into a Duke understandable {@link Command}
 */
public class Parser {

    /**
     * Returns a {@link Command} that can be understood by {@link Duke} and executed after
     * @param fullCommand the String command entered by the user
     * @return Command the Command to be executed
     * @throws DukeException
     */
    public static Command parse(String fullCommand) throws DukeException {
        String[] splitted = fullCommand.split(" ", 2);// splitted contains the keyword and the rest (description or task number)
        switch (splitted[0]) { // switching on the keyword
            case "list":
                if (splitted.length == 2)
                    throw new DukeException("Did you mean just list?");
                else
                    return new ListCommand();
            case "bye":
                if (splitted.length == 2)
                    throw new DukeException("Did you mean just bye?");
                else
                    return new ExitCommand();
            case "done":
                if (splitted.length == 2) {
                    int taskNb = Integer.parseInt(splitted[1]);
                    return new DoneCommand(taskNb - 1);
                } else throw new DukeException("Need a task number after done!");
            case "todo":
                if ((splitted.length == 1) || splitted[1].isEmpty())
                    throw new DukeException("The description of a todo cannot be empty.");
                return new AddCommand(new Todo(splitted[1]));
            case "deadline":
                if ((splitted.length == 1) || splitted[1].isEmpty())
                    throw new DukeException("The description of a deadline cannot be empty.");
                String[] getBy = splitted[1].split("/by ", 2);
                if (getBy.length < 2)
                    throw new DukeException("The description of a deadline must contain /by date!");
                return new AddCommand(new Deadline(getBy[0], getBy[1]));
            case "event":
                if ((splitted.length == 1) || splitted[1].isEmpty())
                    throw new DukeException("The description of an event cannot be empty, and it must contain /at");
                String[] getAt = splitted[1].split("/at ", 2);
                if (getAt.length < 2)
                    throw new DukeException("The description of a deadline must contain /at data and time from-to!");
                return new AddCommand(new Event(getAt[0], getAt[1]));
            case "find":
                if (splitted.length == 2) {
                    return new FindCommand(splitted[1]);
                } else throw new DukeException("Need a word to find! ");
            case "delete":
                if (splitted.length == 2) {
                    int taskNb = Integer.parseInt(splitted[1]);
                    return new DeleteCommand(taskNb - 1);
                } else throw new DukeException("Need a task number after done!");
            case "period":
                if ((splitted.length == 1) || splitted[1].isEmpty())
                    throw new DukeException("The description of a period cannot be empty.");
                String[] getPart = splitted[1].split("/from ", 2);
                if (getPart.length < 2)
                    throw new DukeException("The description of a period must contain /from date!");
                String[] part = getPart[1].split("/to ", 2);
                if (part.length < 2)
                    throw new DukeException("NO");
                try {
                    return new AddCommand(new DoWithinPeriodTasks(getPart[0], part[0], part[1]));
                }
                catch(Exception e){
                    throw new DukeException("NO");
                }
            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
