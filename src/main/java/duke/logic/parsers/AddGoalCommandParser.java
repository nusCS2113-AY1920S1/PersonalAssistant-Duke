package duke.logic.parsers;

import duke.logic.commands.AddGoalCommand;

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
        return new AddGoalCommand();
    }
}
