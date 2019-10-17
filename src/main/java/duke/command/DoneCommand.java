package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class DoneCommand extends ArgCommand {

    @Override
    public void execute(DukeCore core) throws DukeException {
        String taskStr = core.taskList.markDone(getArg());
        taskStr = "Nice! I've marked this task as done:" + System.lineSeparator() + "  " + taskStr;
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        core.ui.print(taskStr);
    }
}
