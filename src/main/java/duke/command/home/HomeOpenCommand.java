package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
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
        core.uiContext.setContext(Context.PATIENT, patient);
        core.ui.print("Accessing details of Bed " + patient.getBedNo());
    }
}
