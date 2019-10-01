package oof;

import oof.command.Command;
import oof.command.CompleteCommand;
import oof.command.ExitCommand;
import oof.command.ListCommand;
import oof.command.AddToDoCommand;
import oof.command.AddDeadlineCommand;
import oof.command.AddEventCommand;
import oof.command.DeleteCommand;
import oof.command.FindCommand;
import oof.command.SnoozeCommand;
import oof.command.ScheduleCommand;
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
        if (line.equals("bye")) {
            return new ExitCommand();
        } else if (line.equals("list")) {
            return new ListCommand();
        } else if (line.startsWith("done")) {
            String[] arr = line.split(" ");
            if (arr.length == 1) {
                throw new OofException("OOPS!!! Please enter a number!");
            }
            String num = arr[1];
            return new CompleteCommand(num);
        } else if (line.startsWith("todo")) {
            line = line.replaceFirst("todo", "");
            return new AddToDoCommand(line);
        } else if (line.startsWith("deadline")) {
            line = line.replaceFirst("deadline", "");
            return new AddDeadlineCommand(line);
        } else if (line.startsWith("event")) {
            line = line.replaceFirst("event", "");
            return new AddEventCommand(line);
        } else if (line.startsWith("delete")) {
            int num = Integer.parseInt(line.split(" ")[1]) - 1;
            return new DeleteCommand(num);
        } else if (line.startsWith("find")) {
            return new FindCommand(line);
        } else if (line.startsWith("snooze")) {
            String[] arr = line.split(" ");
            if (arr.length == 1) {
                throw new OofException("OOPS!!! Please enter a number!");
            }
            String num = arr[1];
            return new SnoozeCommand(num);
        } else if (line.startsWith("schedule")) {
            line = line.replaceFirst("schedule", "");
            return new ScheduleCommand(line);
        } else {
            throw new OofException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
