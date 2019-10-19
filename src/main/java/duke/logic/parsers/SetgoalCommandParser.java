package duke.logic.parsers;

import duke.logic.commands.AddGoalCommand;
import duke.commons.exceptions.DukeException;
import duke.model.Goal;

public class SetgoalCommandParser implements ParserInterface<AddGoalCommand> {

    @Override
    public AddGoalCommand parse(String userInput) throws DukeException {
        String name = userInput.split(" ", 2)[0].trim();
        String info = userInput.split(" ", 2)[1];
        return new AddGoalCommand(new Goal(name, info));
    }
}
