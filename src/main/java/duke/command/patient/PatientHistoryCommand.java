package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientHistoryCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return PatientHistorySpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        Patient patient = (Patient) core.uiContext.getObject();
        patient.appendHistory(getArg());
        patient.updateAttributes();
        core.writeJsonFile();
        core.ui.print("Note appended!");
    }
}
