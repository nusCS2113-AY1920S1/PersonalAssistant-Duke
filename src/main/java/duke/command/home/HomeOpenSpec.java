package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.CommandUtils;
import duke.command.Switch;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class HomeOpenSpec extends ArgSpec {
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
    public void execute(DukeCore core, ArgCommand cmd) throws DukeException {
        String bed = cmd.getSwitchVal("bed");
        Patient patient = CommandUtils.findFromHome(core, bed, cmd.getArg());

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
