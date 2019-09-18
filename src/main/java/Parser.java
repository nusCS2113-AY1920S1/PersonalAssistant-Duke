import duke.command.*;
import duke.dukeexception.DukeException;

import java.util.*;

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
            case "schedule":
                return new ViewScheduleCommand(arguments);
            case "bye":
                return new ByeCommand();
            default:
                throw new DukeException("Please enter a valid command.");
        }
    }
}
