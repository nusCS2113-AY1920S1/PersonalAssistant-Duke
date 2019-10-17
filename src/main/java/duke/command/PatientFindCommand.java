package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class PatientFindCommand extends ArgCommand {

    @Override
    ArgSpec getSpec() {
        return PatientFindSpec.getSpec();
    }

    ;

    @Override
    public void execute(DukeCore core) throws DukeException {
        // TODO: change code to find DukeObjects
        String findStr = "Here are the tasks that contain '" + getArg() + "':";
        findStr = findStr + core.taskList.find(getArg()).replace(System.lineSeparator(),
                System.lineSeparator() + "  ");
        core.ui.print(findStr);
    }
}
