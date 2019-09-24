package parser;


import command.AddCommand;
import command.Command;
import command.ConflictCommand;
import command.DeleteCommand;
import command.DoneCommand;
import command.ExitCommand;
import command.FindCommand;
import command.FreeCommand;
import command.ListCommand;
import command.RemindCommand;
import command.RescheduleCommand;
import command.ScheduleCommand;
import exception.DukeException;

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
     * @throws DukeException If user input is invalid.
     */
    public static Command getCommand(String commandName) throws DukeException {
        switch (commandName) {
        case "todo":
        case "deadline":
        case "event":
            return new AddCommand();

        case "list":
            return new ListCommand();

        case "done":
            return new DoneCommand();

        case "delete":
            return new DeleteCommand();

        case "find":
            return new FindCommand();

        case "bye":
            return new ExitCommand();

        case "schedule":
            return new ScheduleCommand();

        case "remind":
            return new RemindCommand();

        case "reschedule":
            return new RescheduleCommand();

        case "free":
            return new FreeCommand();

        case "conflict":
            return new ConflictCommand();

        default:
            throw new DukeException("☹ OOPS!!! I don't know what that means :-(");
        }
    }
}
