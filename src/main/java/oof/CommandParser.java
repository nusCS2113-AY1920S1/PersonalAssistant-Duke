package oof;

import oof.command.AddDeadlineCommand;
import oof.command.AddEventCommand;
import oof.command.AddToDoCommand;
import oof.command.Command;
import oof.command.CompleteCommand;
import oof.command.DeleteCommand;
import oof.command.ExitCommand;
import oof.command.FindCommand;
import oof.command.ListCommand;
import oof.command.ScheduleCommand;
import oof.command.SnoozeCommand;
import oof.exception.OofException;

/**
 * Represents a parser to process the commands inputted by the user.
 */
public class CommandParser {
    /**
     * Parses the input given by user and calls specific Commands
     * after checking the validity of the input.
     *
     * @param line Command inputted by user.
     * @return Command based on the user input.
     * @throws OofException Catches invalid commands given by user.
     */
    public static Command parse(String line) throws OofException {
        String[] arr = line.split(" ");
        switch (arr[0]) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "done":
            if (arr.length == 1) {
                throw new OofException("OOPS!!! Please enter a number!");
            }
            int completeIndex = Integer.parseInt(arr[1]) - 1;
            return new CompleteCommand(completeIndex);
        case "todo":
            line = line.replaceFirst("todo ", "");
            return new AddToDoCommand(line);
        case "deadline":
            line = line.replaceFirst("deadline ", "");
            return new AddDeadlineCommand(line);
        case "event":
            line = line.replaceFirst("event ", "");
            return new AddEventCommand(line);
        case "delete":
            if (arr.length == 1) {
                throw new OofException("OOPS!!! Please enter a number!");
            }
            int deleteIndex = Integer.parseInt(arr[1]) - 1;
            return new DeleteCommand(deleteIndex);
        case "find":
            return new FindCommand(line);
        case "snooze":
            if (arr.length == 1) {
                throw new OofException("OOPS!!! Please enter a number!");
            }
            int snoozeIndex = Integer.parseInt(arr[1]) - 1;
            return new SnoozeCommand(snoozeIndex);
        case "schedule":
            line = line.replaceFirst("schedule ", "");
            return new ScheduleCommand(line);
        default:
            throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
