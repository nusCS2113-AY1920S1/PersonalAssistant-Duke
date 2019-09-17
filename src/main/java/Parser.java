import command.ByeCommand;
import command.Command;
import command.DeadlineCommand;
import command.DeleteCommand;
import command.DoneCommand;
import command.EventCommand;
import command.FindCommand;
import command.ListCommand;
import command.TodoCommand;
import command.ViewScheduleCommand;
import exception.DukeException;

/**
 * Parse input and execute respective user command.
 */
public class Parser {
    /**
     * Parses user commands.
     * @param input from user
     * @return Command to be executed
     * @throws DukeException if user input is in wrong format
     */
    public static Command parse(String input) throws DukeException {
        String[] splitStr = input.split(" ");
        switch (splitStr[0]) {
        case "list":
            return new ListCommand();
        case "bye":
            return new ByeCommand();
        case "done":
            return new DoneCommand(splitStr);
        case "deadline":
            return new DeadlineCommand(input, splitStr);
        case "todo":
            return new TodoCommand(input, splitStr);
        case "event":
            return new EventCommand(input, splitStr);
        case "delete":
            return new DeleteCommand(splitStr);
        case "find":
            return new FindCommand(input, splitStr);
        case "viewschedule":
            return new ViewScheduleCommand();
        case "snooze":
            return new SnoozeCommand(splitStr);
        case "unsnooze":
            return new UnSnoozeCommand(splitStr);    
        default:
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
