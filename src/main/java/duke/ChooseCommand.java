package duke;

import duke.command.Command;
import duke.command.CompleteCommand;
import duke.command.ExitCommand;
import duke.command.ListCommand;
import duke.command.AddToDoCommand;
import duke.command.AddDeadlineCommand;
import duke.command.AddEventCommand;
import duke.command.DeleteCommand;
import duke.command.FindCommand;
import duke.command.SnoozeCommand;
import duke.command.ScheduleCommand;
import duke.exception.DukeException;

/**
 * Represents a parser to process the commands inputted by the user.
 */
public class ChooseCommand {
    /**
     * Parses the input given by user and calls specific Commands
     * after checking the validity of the input.
     *
     * @param line Command inputted by user.
     * @return Command based on the user input.
     * @throws DukeException Catches invalid commands given by user.
     */
    public static Command choose(String line) throws DukeException {
        if (line.equals("bye")) {
            return new ExitCommand();
        } else if (line.equals("list")) {
            return new ListCommand();
        } else if (line.startsWith("done")) {
            String[] arr = line.split(" ");
            if (arr.length == 1) {
                throw new DukeException("OOPS!!! Please enter a number!");
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
                throw new DukeException("OOPS!!! Please enter a number!");
            }
            String num = arr[1];
            return new SnoozeCommand(num);
        } else if (line.startsWith("schedule")) {
            // View Schedule
            line = line.replaceFirst("schedule", "");
            return new ScheduleCommand(line);
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
