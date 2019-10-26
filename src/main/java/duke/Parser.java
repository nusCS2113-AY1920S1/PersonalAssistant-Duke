package duke;

import duke.commands.AddBarCommand;
import duke.commands.AddCommand;
import duke.commands.AddOverlayCommand;
import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.commands.CopyCommand;
import duke.commands.DeleteBarCommand;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.EditCommand;
import duke.commands.FindCommand;
import duke.commands.GroupCommand;
import duke.commands.HelpCommand;
import duke.commands.ListCommand;
import duke.commands.NewCommand;
import duke.commands.RedoCommand;
import duke.commands.UndoCommand;
import duke.commands.ViewCommand;


/**
 * A class used to interpret the incoming messages and translate them into the appropriate duke.Commands.
 */

public class Parser {

    /**
     * Returns the duke.Commands.duke.Commands.Command object interpreted from the input message,
     * and throws a duke.DukeException otherwise.
     *
     * @param message the input message to be parsed
     * @return the duke.Commands.duke.Commands.Command object interpreted from the input message
     * @throws DukeException in the case of parsing errors
     */
    public static Command parse(String message) throws DukeException {
        switch (message.split(" ")[0]) {
        case "bye":
            if (message.length() == 3) {
                return new ByeCommand();
            }
            break;
        case "list":
            if (message.length() == 4) {
                return new ListCommand();
            }
            break;
        case "delete":
            if (message.length() >= 8) {
                return new DeleteCommand(message);
            }
            break;
        case "deletebar":
            if (message.length() >= 11) {
                return new DeleteBarCommand(message);
            }
            break;
        case "edit":
            if (message.length() >= 6) {
                return new EditCommand(message);
            }
            break;
        case "find":
            if (message.length() >= 6) {
                return new FindCommand(message);
            }
            break;
        case "done":
            if (message.length() >= 6) {
                return new DoneCommand(message);
            }
            break;
        case "new":
            if (message.length() >= 5) {
                return new NewCommand(message);
            }
            break;
        case "help": {
            return new HelpCommand(message);
        }
        case "view":
            if (message.length() >= 6) {
                return new ViewCommand(message);
            }
            break;
        case "addbar":
            if (message.length() >= 7) {
                return new AddBarCommand(message);
            }
            break;
        case "overlay":
            if (message.length() >= 8) {
                return new AddOverlayCommand(message);
            }
            break;
        case "group":
            if (message.length() >= 7) {
                return new GroupCommand(message);
            }
            break;
        case "copy":
            if (message.length() >= 6) {
                return new CopyCommand(message);
            }
            break;
        case "redo":
            if (message.length() == 4) {
                return new RedoCommand();
            }
            break;
        case "undo":
            if (message.length() == 4) {
                return new UndoCommand();
            }
            break;
        default:
            return new AddCommand(message);
        }
        throw new DukeException(message);
    }
}
