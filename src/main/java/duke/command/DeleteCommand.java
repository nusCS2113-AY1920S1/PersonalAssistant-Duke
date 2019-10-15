package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class DeleteCommand extends ArgCommand {

    @Override
    String getEmptyArgMsg() {
        return "You didn't tell me which task to delete!";
    }

    @Override
    ArgLevel getCmdArgLevel() {
        return ArgLevel.REQUIRED;
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        String delStr = core.taskList.deleteTask(getArg());
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.ui.print(core.taskList.getDelReport(System.lineSeparator() + "  " + delStr, 1));
    }
}
