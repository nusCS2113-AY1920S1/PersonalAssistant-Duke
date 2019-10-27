package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.Context;

public class PatientOpenCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return PatientOpenSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        String impressionId = getSwitchVal("impression");
        Patient patient = (Patient) core.uiContext.getObject();
        core.uiContext.setContext(Context.IMPRESSION, patient.getImpression(impressionId));
        core.ui.print("Opening impression");
    }
}
