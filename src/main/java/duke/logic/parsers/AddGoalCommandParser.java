package duke.logic.parsers;

import duke.logic.commands.AddGoalCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Goal;

/**
 * Parser class to handle setting of goals.
 */
public class AddGoalCommandParser implements ParserInterface<AddGoalCommand> {

    /**
     * Parse user input and return AddGoalCommand.
     * @param userInput String input by user
     * @return <code>AddGoalCommand</code> Command object encapsulating the goal object
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public AddGoalCommand parse(String userInput) throws DukeException {
        String name = userInput.split(" ", 2)[0].trim();
        String info = userInput.split(" ", 2)[1];
        return new AddGoalCommand(new Goal(name, info));
    }
}
