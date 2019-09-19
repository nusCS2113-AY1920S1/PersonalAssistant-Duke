package duke.task;

import duke.command.AddDeadLineCommand;
import duke.command.AddEventCommand;
import duke.command.AddToDoCommand;
import duke.command.DeleteTaskCommand;
import duke.command.ExitCommand;
import duke.command.FindTaskCommand;
import duke.command.ListTaskCommand;
import duke.command.MarkTaskAsDoneCommand;
import duke.command.Command;
import duke.command.ViewSchedule;

/**
 * Takes in a string and parses it to return a valid command to be ran.
 */
public class Parser {
    /**
     * Takes in input from user and returns a command based on the input given.
     * @param input String given by the user
     * @return The command object corresponding to the user input
     * @throws DukeException Thrown when an invalid input is given
     */
    public static Command parse(String input) throws DukeException {
        if (input.startsWith("todo ")) {
            return new AddToDoCommand(false, input);
        } else if (input.startsWith("event ")) {
            return new AddEventCommand(false, input);
        } else if (input.startsWith("deadline ")) {
            return new AddDeadLineCommand(false, input);
        } else if (input.startsWith("done ")) {
            return new MarkTaskAsDoneCommand(false, input);
        } else if (input.equals("list")) {
            return new ListTaskCommand(false, "");
        }  else if (input.startsWith("find ")) {
            return new FindTaskCommand(false, input);
        } else if (input.startsWith("delete ")) {
            return new DeleteTaskCommand(false, input);
        } else if (input.startsWith("view")) {
            return new ViewSchedule(false, input);
        } else if (input.equals("bye")) {
            return new ExitCommand(true, "");
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
