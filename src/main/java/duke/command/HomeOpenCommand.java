package duke.command;

import duke.DukeCore;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.Context;

public class HomeOpenCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return HomeOpenSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        String bed = getSwitchVal("bed");
        Patient patient = core.patientMap.getPatient(bed);

        core.context.setContext(Context.PATIENT, patient);
    }
}
