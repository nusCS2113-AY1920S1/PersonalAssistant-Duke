package duke.logic.parsers;

import duke.logic.commands.AddGoalCommand;

import java.util.HashMap;

/**
 * Parser class to handle setting of goals.
 */
public class AddGoalCommandParser implements ParserInterface<AddGoalCommand> {

    /**
     * Parse user input and return AddGoalCommand.
     * @param userInputStr String input by user
     * @return <code>AddGoalCommand</code> Command object encapsulating the goal object
     */
    @Override
    public AddGoalCommand parse(String userInputStr) {
        HashMap<String, String> argumentsMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);

        return new AddGoalCommand();
    }
}
