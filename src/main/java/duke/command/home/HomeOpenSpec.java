package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.CommandUtils;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class HomeOpenSpec extends ObjSpec {
    private static final HomeOpenSpec spec = new HomeOpenSpec();

    public static HomeOpenSpec getSpec() {
        return spec;
    }

    private HomeOpenSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("impression", String.class, true, ArgLevel.NONE, "im")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String bed = cmd.getSwitchVal("bed");
        Patient patient = CommandUtils.findFromHome(core, bed, cmd.getArg());
        if (patient == null) {
            core.search(core.patientData.findPatients(cmd.getArg()), cmd);
        } else {
            executeWithObj(core, patient);
        }
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        Patient patient = (Patient) obj;
        if (cmd.isSwitchSet("impression")) {
            Impression primaryDiagnosis = patient.getPrimaryDiagnosis();

            if (primaryDiagnosis != null) {
                core.uiContext.setContext(Context.PATIENT, patient);
                core.uiContext.setContext(Context.IMPRESSION, primaryDiagnosis);
                core.updateUi("Accessing primary diagnosis of " + patient.getName());
            } else {
                throw new DukeException(patient.getName() + " has no primary diagnosis at the moment!");
            }
        } else {
            core.uiContext.setContext(Context.PATIENT, patient);
            core.updateUi("Accessing details of " + patient.getName());
        }
    }
}
