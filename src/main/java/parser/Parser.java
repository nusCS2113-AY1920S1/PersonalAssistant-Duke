package parser;


import command.AddCommand;
import command.Command;
import command.DeleteCommand;
import command.DoneCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.RemindCommand;
import command.RescheduleCommand;
import command.ScheduleCommand;
import exception.DukeException;

/**
 * Parses the command line from user input to tokens and
 * packages the tokens to <code>Command</code> object.
 */
public class Parser {

    /**
     * Converts the <code>String</code> fullCommand into <code>Command</code> object.
     * Returns the <code>Command</code> object.
     *
     * @param fullCommand The command line read from user input.
     * @return <code>Command</code> object converted from fullCommand.
     * @throws DukeException If user input is invalid.
     */
    public static Command parse(String fullCommand) throws DukeException {
        CommandParams commandParams = new CommandParams(fullCommand);

        switch (commandParams.getCommandType()) {
        case "todo":
        case "deadline":
        case "event":
            return new AddCommand(commandParams);

        case "list":
            return new ListCommand(commandParams);

        case "done":
            return new DoneCommand(commandParams);

        case "delete":
            return new DeleteCommand(commandParams);

        case "find":
            return new FindCommand(commandParams);

        case "bye":
            return new ExitCommand(commandParams);

        case "schedule":
            return new ScheduleCommand(commandParams);

        case "remind":
            return new RemindCommand(commandParams);

        case "reschedule":
            return new RescheduleCommand(commandParams);

        default:
            throw new DukeException("☹ OOPS!!! I don't know what that means :-(");
        }

    }
}
