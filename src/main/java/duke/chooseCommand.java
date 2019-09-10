package duke;

import duke.command.addDeadlineCommand;
import duke.command.addEventCommand;
import duke.command.AddToDoCommand;
import duke.command.completeCommand;
import duke.command.deleteCommand;
import duke.command.exitCommand;
import duke.command.findCommand;
import duke.command.ListCommand;
import duke.command.Command;
import duke.exception.DukeException;

/**
 * Represents a parser to process the comands inputted by the user.
 */
public class chooseCommand {
    /**
     * Main method of the <code>chooseCommand</code> class.
     * Parses the input given by user and calls specific <code>Commands</code>
     * after checking the validity of the input.
     * @param line Command inputted by user.
     * @return <code>Command</code> based on the user input.
     * @throws DukeException Catches invalid commands given by user.
     */
    static Command choose (String line) throws DukeException {
        if (line.equals("bye")) {
            return new exitCommand();
        }
        else if (line.equals("list")) {
            return new ListCommand();
        }
        else if (line.startsWith("done")) {
            int num = Integer.parseInt(line.split(" ")[1]) - 1;
            return new completeCommand(num);
        }
        else if (line.startsWith("todo")) {
            line = line.replaceFirst("todo", "");
            return new AddToDoCommand(line);
        }
        else if (line.startsWith("deadline")) {
            line = line.replaceFirst("deadline", "");
            return new addDeadlineCommand(line);
        }
        else if (line.startsWith("event")) {
            line = line.replaceFirst("event", "");
            return new addEventCommand(line);
        }
        else if (line.startsWith("delete")) {
            int num = Integer.parseInt(line.split(" ")[1]) - 1;
            return new deleteCommand(num);
        }
        else if (line.startsWith("find")) {
            return new findCommand(line);
        }
        else {
            throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
