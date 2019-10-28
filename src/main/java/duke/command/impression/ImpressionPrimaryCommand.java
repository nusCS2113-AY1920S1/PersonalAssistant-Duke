package duke.command.impression;

import duke.DukeCore;
import duke.command.Command;
import duke.data.Patient;

public class ImpressionPrimaryCommand extends Command {

    @Override
    public void execute(DukeCore core) {
        // TODO: implement setPrimaryDiagnosis in Patient
        // TODO: get parent from helper class
        Patient patient = (Patient) core.uiContext.getObject().getParent();

    }
}
