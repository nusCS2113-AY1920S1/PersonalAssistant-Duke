package owlmoney.logic.parser;

import java.util.Scanner;

import owlmoney.logic.command.AddCommand;
import owlmoney.logic.command.Command;
import owlmoney.logic.command.DeleteCommand;
import owlmoney.logic.command.DoneCommand;
import owlmoney.logic.command.ExitCommand;
import owlmoney.logic.command.FindCommand;
import owlmoney.logic.command.ListCommand;
import owlmoney.logic.command.ReminderCommand;
import owlmoney.logic.command.RescheduleCommand;
import owlmoney.logic.command.ViewCommand;
import owlmoney.logic.exception.DukeException;
import owlmoney.model.task.Deadline;
import owlmoney.model.task.Event;
import owlmoney.model.task.RecurringTask;
import owlmoney.model.task.Todo;

/**
 * Parser for commands entered by the Duke user. It reads from standard input and
 * returns Command objects.
 */

public class Parser {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Checks if the user has any more input during input redirection.
     *
     * @return the results after checking.
     */

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Takes in user input line by line.
     *
     * @return The corresponding action done by {@link #parseActionMenu(String, String)}.
     * @throws DukeException thrown by {@link #parseActionMenu(String, String)}.
     */
    public Command parseLine() throws DukeException {
        String input = scanner.nextLine();
        parseIsBlank(input);
        String action = parseAction(input).trim();
        String data = parseData(input, action);
        return parseActionMenu(action, data);
    }

    /**
     * Checks if the inputs are blank spaces or empty.
     *
     * @param input The raw input that the user enters.
     * @throws DukeException if the input is blank or empty.
     */

    private void parseIsBlank(String input) throws DukeException {
        if (input.isBlank() || input.isEmpty()) {
            throw new DukeException("User input is empty, please enter something");
        }
    }

    /**
     * Parses a input and returns a sanitised action string.
     *
     * @param input The raw input that the user enters.
     * @return The corresponding sanitised action field without the data.
     * @throws DukeException if the input does not conform to specifications.
     */
    private String parseAction(String input) throws DukeException {
        String[] inputSplit = input.split(" ", 2);
        if ("bye".equals(inputSplit[0]) && inputSplit.length != 1) {
            throw new DukeException("bye should not contain trailing arguments");
        } else if ("list".equals(inputSplit[0]) && inputSplit.length != 1) {
            throw new DukeException("list should not contain trailing arguments");
        }
        return inputSplit[0];
    }

    /**
     * Parses a input and returns a sanitised data string.
     *
     * @param input The raw input that the user enters.
     * @param action The action identified by {@link #parseAction(String)}.
     * @return The corresponding sanitised data field without the action.
     */

    private String parseData(String input, String action) {
        String data = input.substring(action.length(), input.length());
        return data;
    }

    /**
     * Parses a command from its two parts and returns a Command object.
     *
     * @param action The command's first word, which dictates the rest of the
     *            command's structure.
     * @param data The data associated with the command, which may be the empty string.
     * @return The corresponding Command object.
     * @throws DukeException if an input is not recognised
     */

    private Command parseActionMenu(String action, String data) throws DukeException {
        switch (action) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "done":
            return new DoneCommand(data);
        case "delete":
            return new DeleteCommand(data);
        case "find":
            return new FindCommand(data);
        case "deadline":
            return new AddCommand(Deadline.create(data));
        case "event":
            return new AddCommand(Event.create(data));
        case "todo":
            return new AddCommand(Todo.create(data));
        case "reschedule":
            return new RescheduleCommand(data);
        case "recurring":
            return new AddCommand(RecurringTask.create(data));
        case "view":
            return new ViewCommand(data);
        case "reminder":
            return new ReminderCommand(data);
        default:
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
