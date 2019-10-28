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
        int index = switchToInt("index");
        Patient patient = CommandUtils.findPatient(core, bed, index);

        if (isSwitchSet("impression")) {
            Impression primaryImpression = patient.getPrimaryDiagnosis();

            if (patient.getPrimaryDiagnosis() != null) {
                core.uiContext.setContext(Context.IMPRESSION, primaryImpression);
            } else {
                throw new DukeException("The specified patient has no primary diagnosis at the moment!");
            }
        } else {
            core.uiContext.setContext(Context.PATIENT, patient);
            core.ui.print("Accessing details of Bed " + patient.getBedNo());
        }
    }
}
