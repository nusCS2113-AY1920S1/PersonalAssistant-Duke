package chronologer.parser;

import chronologer.command.AddCommand;
import chronologer.command.Command;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required to add a todo task.
 *
 * @author Fauzan
 * @version v1.0
 */
public class TodoParser extends DescriptionParser {

    TodoParser(String userInput, String command) {
        super(userInput, command);
    }

    /**
     * Parses the user's input into information to be passed into the Command class.
     * @return The command to add the task with inputted description
     * @throws ChronologerException if user's input is in an invalid format
     */
    public Command parse() throws ChronologerException {
        super.extract();

        return new AddCommand(command, taskDescription, null, null);
    }
}
