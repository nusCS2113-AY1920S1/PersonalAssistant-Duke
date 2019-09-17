import duke.command.AddDeadlineCommand;
import duke.command.AddEventCommand;
import duke.command.AddTodoCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.ReminderCommand;
import duke.command.SnoozeCommand;
import duke.dukeexception.DukeException;
import java.util.Arrays;
import java.util.List;

class Parser {
    /**
     * Returns the command to execute after parsing user input.
     *
     * @param input the user input
     * @return the command to execute
     * @throws DukeException the exception if user input is invalid
     */
    public static Command parse(String input) throws DukeException {
        List<String> words = Arrays.asList(input.split(" "));
        String keyword = words.get(0);
        List<String> arguments = words.subList(1, words.size());

        switch (keyword) {
            case "list":
                return new ListCommand();
            case "find":
                return new FindCommand(arguments);
            case "delete":
                return new DeleteCommand(arguments);
            case "done":
                return new DoneCommand(arguments);
            case "todo":
                return new AddTodoCommand(arguments);
            case "deadline":
                return new AddDeadlineCommand(arguments);
            case "event":
                return new AddEventCommand(arguments);
            case "reminder":
                return new ReminderCommand();
            case "snooze":
                return new SnoozeCommand(arguments);
            case "bye":
                return new ByeCommand();
            default:
                throw new DukeException("Please enter a valid command.");
        }
    }
}
