package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

public class FindCommand extends ArgCommand {
    public FindCommand() {
        emptyArgMsg = "You didn't tell me what to look for!";
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        String findStr = "Here are the tasks that contain '" + arg + "':";
        findStr = findStr + ctx.taskList.find(arg).replace(System.lineSeparator(),
                System.lineSeparator() + "  ");
        ctx.ui.print(findStr);
    }
}
