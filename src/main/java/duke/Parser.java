package duke;

import duke.commands.AddCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DeleteMultipleCommand;
import duke.commands.DoneCommand;
import duke.commands.ExitCommand;
import duke.commands.UnknownCommand;
import duke.commands.ReminderCommand;
import duke.commands.ListCommand;
import duke.commands.ViewScheduleCommand;
import duke.commands.FileCommand;
import duke.commands.FindCommand;
import duke.commands.SnoozeCommand;
import duke.commands.SetCommand;
import duke.commands.FindFreeCommand;
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

            case "delete-multiple":
                command = new DeleteMultipleCommand(components[1], components.length);
                break;

            case "find":
                command = new FindCommand(components, input);
                break;

            case "view-schedule":
                command = new ViewScheduleCommand(components, input);
                break;

            case "todo":
            case "deadline":
            case "do-within":
            case "do-after":
            case "event":
            case "tentative":
            case "fixed":
            case "recurring":
                command = new AddCommand(components, input);
                break;

            case "set-tentative":
                command = new SetCommand(components, input);
                break;

            case "bye":
                command = new ExitCommand();
                break;

            case "snooze":
                command = new SnoozeCommand(Integer.parseInt(components[1]) - 1, components, input);
                break;

            case "free":
                command = new FindFreeCommand(components[1]);
                break;

            case "file":
                command = new FileCommand(components[1], input);
                break;

            default:
                command = new UnknownCommand();
            }
            return command;
        } catch (NumberFormatException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InputException("Please provide an index or action. Eg. 'done 5', 'delete 3', 'file view'");
        }
    }
}