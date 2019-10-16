package duke.parsers;

import duke.commands.AddGoalCommand;
import duke.exceptions.DukeException;
import duke.tasks.Goal;

public class SetgoalCommandParser implements ParserInterface<AddGoalCommand> {

    @Override
    public AddGoalCommand parse(String UserInput) throws DukeException {
        String name = UserInput.split(" ", 2)[0].trim();
        String info = UserInput.split(" ", 2)[1];
        return new AddGoalCommand(new Goal(name, info));
    }
}
