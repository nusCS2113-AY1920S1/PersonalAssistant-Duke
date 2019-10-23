package parser;

import command.AddCommand;
import command.Command;
import exception.DukeException;

/**
 * Extract the components required to add a todo task.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class TodoParser extends DescriptionParser {

    public TodoParser(String userInput, String command) {
        super(userInput, command);
    }

    /**
     * Parses the user's input into information to be passed into the Command class.
     * @return The command to add the task with inputted description
     * @throws DukeException if user's input is in an invalid format
     */
    public Command parse() throws DukeException {
        super.extract();

        return new AddCommand(command, taskDescription, null, null);
    }
}
