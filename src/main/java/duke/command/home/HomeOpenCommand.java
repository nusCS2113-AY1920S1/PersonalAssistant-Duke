package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.command.CommandUtils;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class HomeOpenCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return HomeOpenSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        String bed = getSwitchVal("bed");
        Patient patient = CommandUtils.findPatient(core, bed, getArg());

        if (isSwitchSet("impression")) {
            Impression primaryDiagnosis = patient.getPrimaryDiagnosis();

            if (primaryDiagnosis != null) {
                core.uiContext.setContext(Context.PATIENT, patient);
                core.uiContext.setContext(Context.IMPRESSION, primaryDiagnosis);
                core.ui.print("Accessing primary diagnosis of " + patient.getName());
            } else {
                throw new DukeException(patient.getName() + " has no primary diagnosis at the moment!");
            }
        } else {
            core.uiContext.setContext(Context.PATIENT, patient);
            core.ui.print("Accessing details of " + patient.getName());
        }
    }
}
