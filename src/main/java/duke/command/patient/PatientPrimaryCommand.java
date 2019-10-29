package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.command.CommandUtils;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientPrimaryCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return PatientPrimarySpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        Patient patient = (Patient) core.uiContext.getObject();
        Impression impression = (Impression) CommandUtils.findObject(core, patient, "impression",
                getSwitchVal("name"), switchToInt("index"));
        patient.setPrimaryDiagnosis(impression.getName());
        patient.updateAttributes();
        core.writeJsonFile();
        core.ui.print("Primary diagnosis set!");
    }
}
