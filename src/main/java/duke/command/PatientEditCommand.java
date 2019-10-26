package duke.command;

import duke.DukeCore;
//import duke.data.Patient;
import duke.exception.DukeException;

public class PatientEditCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return PatientEditSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        //Patient patient = (Patient) core.uiContext.getObject();

        // TODO: edit patient
    }
}
