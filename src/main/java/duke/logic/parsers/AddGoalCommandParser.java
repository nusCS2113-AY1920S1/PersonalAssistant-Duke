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
     */
    @Override
    public AddGoalCommand parse(String userInput) {
        try {
            String name = userInput.split(" ", 2)[0].trim();
            String info = userInput.split(" ", 2)[1];
            Goal goal = new Goal(name,info);
            return new AddGoalCommand(new Goal(name, info));
        } catch (DukeException e) {
            return new AddGoalCommand(false, e.getMessage());
        }
    }
}
