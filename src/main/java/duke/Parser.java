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
        if (message.equals("bye")) {
            return new ByeCommand();
        } else if (message.equals("list")) {
            return new ListCommand();
        } else if (message.length() >= 8 && message.substring(0, 6).equals("delete")) {
            return new DeleteCommand(message);
        } else if (message.length() >= 6 && message.substring(0, 4).equals("find")) {
            return new FindCommand(message);
        } else if (message.length() >= 6 && message.substring(0, 4).equals("done")) {
            return new DoneCommand(message);
        } else if (message.length() >= 5 && message.substring(0, 3).equals("new")) {
            return new NewCommand(message);
        } else if (message.length() >= 6 && message.substring(0, 4).equals("help")) {
            return new HelpCommand(message);
        } else if (message.length() >= 6 && message.substring(0, 4).equals("view")) {
            return new ViewCommand(message);
        } else if (message.length() >= 8 && message.substring(0, 6).equals("addbar")) {
            return new AddBarCommand(message);

        } else if (message.length() >= 8 && message.substring(0, 7).equals("overlay")) {
            //overlay command
            //System.out.println("je");
            return new AddOverlayCommand(message);
        } else if (message.length() >= 7 && message.substring(0, 5).equals("group")) {
            return new GroupCommand(message);
        } else if (message.length() >= 6 && message.substring(0,4).equals("copy")) {
            return new CopyCommand(message);
        } else {
            return new AddCommand(message);
        }
    }
}
