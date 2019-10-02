package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class FindCommand extends ArgCommand {
    public FindCommand() {
        emptyArgMsg = "You didn't tell me what to look for!";
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        String findStr = "Here are the tasks that contain '" + arg + "':";
        findStr = findStr + core.taskList.find(arg).replace(System.lineSeparator(),
                System.lineSeparator() + "  ");
        core.ui.print(findStr);
    }
}
