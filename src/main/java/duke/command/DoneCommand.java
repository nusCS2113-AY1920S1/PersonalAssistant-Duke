package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class DoneCommand extends ArgCommand {
    @Override
    public void execute(DukeCore core) throws DukeException {
        String taskStr = core.taskList.markDone(arg);
        taskStr = "Nice! I've marked this task as done:" + System.lineSeparator() + "  " + taskStr;
        core.storage.writeTaskFile(core.taskList.getFileStr());
        core.ui.print(taskStr);
    }
}
