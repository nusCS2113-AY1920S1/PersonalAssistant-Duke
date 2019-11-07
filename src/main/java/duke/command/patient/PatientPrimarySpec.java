package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.CommandUtils;
import duke.command.Switch;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientPrimarySpec extends ArgSpec {
    private static final PatientPrimarySpec spec = new PatientPrimarySpec();

    public static PatientPrimarySpec getSpec() {
        return spec;
    }

    private PatientPrimarySpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        Patient patient = (Patient) core.uiContext.getObject();
        Impression impression = (Impression) CommandUtils.findFromPatient(core, patient, "impression", cmd.getArg());
        patient.setPrimaryDiagnosis(impression.getName());
        core.writeJsonFile();
        core.updateUi("Primary diagnosis set!");
    }
}
