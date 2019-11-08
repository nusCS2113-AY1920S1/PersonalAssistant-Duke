package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

public class HomeOpenSpec extends HomeObjSpec {
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
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        Patient patient = (Patient) obj;
        if (cmd.isSwitchSet("impression")) {
            Impression primaryDiagnosis = patient.getPrimaryDiagnosis();

            if (primaryDiagnosis != null) {
                core.uiContext.open(patient);
                core.uiContext.open(primaryDiagnosis);
                core.updateUi("Accessing primary diagnosis of " + patient.getName());
            } else {
                throw new DukeException(patient.getName() + " has no primary diagnosis at the moment!");
            }
        } else {
            core.uiContext.open(patient);
            core.updateUi("Accessing details of " + patient.getName());
        }
    }
}
