package duke.parser;


import duke.command.AddExpenseCommand;
import duke.command.BudgetCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.ExitCommand;
import duke.command.FilterCommand;
import duke.command.SortCommand;
import duke.command.ViewCommand;
import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses the command line from user input to tokens and
 * packages the tokens to {@code Command} object.
 */
public class Parser {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    /**
     * Returns the command with the name commandName.
     *
     * @param commandName The name of the command.
     * @return {@code Command} object converted from fullCommand.
     * @throws DukeException If user input is invalid.
     */
    public static Command parseCommand(String commandName) throws DukeException {
        switch (commandName) {
        case "add":
            return new AddExpenseCommand();

        case "delete":
            return new DeleteCommand();

        case "sort":
            return new SortCommand();

        case "view":
            return new ViewCommand();

        case "filter":
            return new FilterCommand();

        case "budget":
            return new BudgetCommand();

        case "bye":
            return new ExitCommand();

        default:
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_NAME_UNKNOWN, commandName));
        }
    }

    /**
     * Converts a LocalDateTime to a user readable string.
     *
     * @param localDateTime LocalDateTime object that we wish to convert
     * @return String that is a formatted date and time
     */
    public static String formatTime(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }
    /**
     * Converts a {@code String} to a {@code LocalDateTime}.
     *
     * @param string {@code String} to convert.
     * @return {@code LocalDateTime} corresponding to the string.
     * @throws DukeException if the string cannot be parsed into a {@code LocalDateTime} object.
     */
    public static LocalDateTime parseTime(String string) throws DukeException {
        try {
            return LocalDateTime.parse(string, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_PARSER_TIME_INVALID, string));
        }
    }
}
