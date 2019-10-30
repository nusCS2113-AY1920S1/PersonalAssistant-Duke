package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class PatientNewCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return PatientNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        Patient patient = (Patient) core.uiContext.getObject();
        Impression impression = new Impression(getArg(), getSwitchVal("description"), patient);
        patient.addNewImpression(impression);
        patient.updateAttributes();
        core.writeJsonFile();
        core.ui.print("Impression added:\n" + patient.getImpression(impression.getName()).toString());

        if (isSwitchSet("go")) {
            core.uiContext.setContext(Context.IMPRESSION, impression);
        }
    }
}
