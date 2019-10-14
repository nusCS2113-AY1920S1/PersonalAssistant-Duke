package spinbox;

import spinbox.commands.AddCommand;
import spinbox.commands.Command;
import spinbox.commands.DeleteCommand;
import spinbox.commands.MultipleCommand;
import spinbox.commands.DoneCommand;
import spinbox.commands.ExitCommand;
import spinbox.commands.UnknownCommand;
import spinbox.commands.ReminderCommand;
import spinbox.commands.ListCommand;
import spinbox.commands.ViewScheduleCommand;
import spinbox.commands.FileCommand;
import spinbox.commands.FindCommand;
import spinbox.commands.SnoozeCommand;
import spinbox.commands.SetCommand;
import spinbox.commands.FindFreeCommand;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;

public class Parser {
    /**
     * Parses an input string into a workable command.
     * @param input user typed in this string.
     * @return a Command that can executed.
     * @throws SpinBoxException Storage errors or input errors.
     */
    public static Command parse(String input) throws SpinBoxException {
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
            case "done-multiple":
                command = new MultipleCommand(components[0], components[1], components.length);
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