package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class DeleteCommand extends ArgCommand {

    public DeleteCommand() {
        emptyArgMsg = "You didn't tell me which task to delete!";
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        String delStr = core.taskList.deleteTask(arg);
        core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        core.ui.print(core.taskList.getDelReport(System.lineSeparator() + "  " + delStr, 1));
    }
}
