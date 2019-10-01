import command.*;
import exception.DukeException;

import java.text.ParseException;

/**
 * Parse input and execute respective user command.
 */
public class Parser{
    public static Command parse(String input) throws DukeException, ParseException {
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
            case "upcoming":
                return new UpcomingCommand();
            case "viewschedule":
                return new ViewScheduleCommand();
            case "snooze":
                return new SnoozeCommand(splitStr);
            case "unsnooze":
                return new UnSnoozeCommand(splitStr);
            case "duration" :
                return new FixedDurationCommand(input, splitStr);
            case "doafter" :
                return new DoAfterCommand(input, splitStr);
            default:
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");

        }
    }
}
