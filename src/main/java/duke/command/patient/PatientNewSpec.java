package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class PatientNewSpec extends ArgSpec {

    private static final PatientNewSpec spec = new PatientNewSpec();

    public static PatientNewSpec getSpec() {
        return spec;
    }

    private PatientNewSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("name", String.class, false, ArgLevel.REQUIRED, "n"),
                new Switch("description", String.class, false, ArgLevel.REQUIRED, "desc")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        Patient patient = (Patient) core.uiContext.getObject();
        Impression impression = new Impression(cmd.getSwitchVal("name"), cmd.getSwitchVal("description"), patient);
        patient.addNewImpression(impression);
        patient.updateAttributes();
        core.writeJsonFile();
        core.updateUi("Impression added:\n" + patient.getImpression(impression.getName()).toString());

        if (cmd.isSwitchSet("go")) {
            core.uiContext.setContext(Context.IMPRESSION, impression);
        }
    }
}
