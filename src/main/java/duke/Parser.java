package duke;

import duke.commands.Command;
import duke.commands.AddCommand;
import duke.commands.FindCommand;
import duke.commands.DeleteCommand;
import duke.commands.ExitCommand;
import duke.commands.UnknownCommand;
import duke.commands.ReminderCommand;
import duke.commands.ListCommand;
import duke.commands.DoneCommand;
import duke.exceptions.DukeException;
import duke.exceptions.InputException;

public class Parser {
    /**
     * Parses an input string into a workable command.
     * @param input user typed in this string.
     * @return a Command that can executed.
     * @throws DukeException Storage errors or input errors.
     */
    public static Command parse(String input) throws DukeException {
        Command command;
        String[] components = input.split(" ");

        try {
            switch (components[0]) {
            case "done":
                command = new DoneCommand(Integer.parseInt(components[1]) - 1);
                break;

            case "list":
                command = new ListCommand();
                break;

            case "remind":
                command = new ReminderCommand();
                break;

            case "delete":
                command = new DeleteCommand(Integer.parseInt(components[1]) - 1);
                break;

            case "find":
                command = new FindCommand(components, input);
                break;

            case "todo":
            case "deadline":
            case "do-after":
            case "event":
            case "fixed":
                command = new AddCommand(components, input);
                break;

            case "bye":
                command = new ExitCommand();
                break;

            default:
                command = new UnknownCommand();
            }
            return command;
        } catch (NumberFormatException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InputException("Please provide an index. Eg. 'done 5' or 'delete 3'");
        }
    }
}
