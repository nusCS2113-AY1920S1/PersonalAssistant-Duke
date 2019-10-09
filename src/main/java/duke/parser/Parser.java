package duke.parser;

import duke.command.AddExpenseCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exception.DukeRuntimeException;

/**
 * Parses the command line from user input to tokens and
 * packages the tokens to {@code Command} object.
 */
public class Parser {

    /**
     * Returns the command with the name commandName.
     *
     * @param commandName The name of the command.
     * @return {@code Command} object converted from fullCommand.
     * @throws DukeRuntimeException If user input is invalid.
     */
    public static Command getCommand(String commandName) throws DukeRuntimeException {
        switch (commandName) {
        case "add":
            return new AddExpenseCommand();

        case "list":
            return new ListCommand();

        case "delete":
            return new DeleteCommand();

        case "find":
            return new FindCommand();

        case "bye":
            return new ExitCommand();

        default:
            throw new DukeRuntimeException("☹ OOPS!!! I don't know what that means :-(");
        }
    }
}
