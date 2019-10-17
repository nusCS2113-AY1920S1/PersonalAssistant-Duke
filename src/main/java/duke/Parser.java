package duke;

import duke.commands.Command;
import duke.commands.ViewCommand;
import duke.commands.GroupCommand;
import duke.commands.NewCommand;
import duke.commands.HelpCommand;
import duke.commands.CopyCommand;
import duke.commands.ByeCommand;
import duke.commands.ListCommand;
import duke.commands.AddOverlayCommand;
import duke.commands.DeleteCommand;
import duke.commands.FindCommand;
import duke.commands.DoneCommand;
import duke.commands.AddBarCommand;
import duke.commands.AddCommand;



/**
 * A class used to interpret the incoming messages and translate them into the appropriate duke.Commands.
 */

class Parser {

    /**
     * Returns the duke.Commands.duke.Commands.Command object interpreted from the input message,
     * and throws a duke.DukeException otherwise.
     *
     * @param message the input message to be parsed
     * @return the duke.Commands.duke.Commands.Command object interpreted from the input message
     * @throws DukeException in the case of parsing errors
     */
    static Command parse(String message) throws DukeException {
        switch (message.split(" ")[0]) {
        case "bye":
            if (message.length() == 3) return new ByeCommand();
        case "list":
            if (message.length() == 4) return new ListCommand();
        case "delete":
            if (message.length() >= 8) return new DeleteCommand(message);
        case "find":
            if (message.length() >= 6) return new FindCommand(message);
        case "done":
            if (message.length() >= 6) return new DoneCommand(message);
        case "new":
            if (message.length() >= 5) return new NewCommand(message);
        case "help":
            return new HelpCommand(message);
        case "view":
            if (message.length() >= 6) return new ViewCommand(message);
        case "addbar":
            if (message.length() >= 8) return new AddBarCommand(message);
        case "overlay":
            if (message.length() >= 8) return new AddOverlayCommand(message);
        case "group":
            if (message.length() >= 7) return new GroupCommand(message);
        case "copy":
            if (message.length() >= 6) return new CopyCommand(message);
        default:
            return new AddCommand(message);
        }
    }
}
