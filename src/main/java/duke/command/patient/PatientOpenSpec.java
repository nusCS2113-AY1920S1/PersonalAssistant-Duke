package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.CommandUtils;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

import java.util.HashMap;
import java.util.Map;

public class PatientOpenSpec extends ArgSpec {
    private static final PatientOpenSpec spec = new PatientOpenSpec();

    public static PatientOpenSpec getSpec() {
        return spec;
    }

    private PatientOpenSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("impression", String.class, true, ArgLevel.NONE, "im"),
                new Switch("critical", String.class, true, ArgLevel.NONE, "c"),
                new Switch("investigation", String.class, true, ArgLevel.NONE, "inv")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        Map<String, Boolean> conditions = new HashMap<>();
        conditions.put("impression", cmd.isSwitchSet("impression"));
        conditions.put("critical", cmd.isSwitchSet("critical"));
        conditions.put("investigation", cmd.isSwitchSet("investigation"));

        String type = null;
        for (Map.Entry<String, Boolean> condition : conditions.entrySet()) {
            if (condition.getValue()) {
                if (type == null) {
                    type = condition.getKey();
                } else {
                    throw new DukeException("Please provide at most 1 unique type (IMPRESSION, CRITICAL or "
                            + "INVESTIGATION that you wish to open!");
                }
            }
        }

        Patient patient = (Patient) core.uiContext.getObject();
        DukeObject object = CommandUtils.findFromPatient(core, patient, type, cmd.getArg());

        if (object instanceof Impression) {
            core.uiContext.setContext(Context.IMPRESSION, object);
        } else {
            core.uiContext.setContext(Context.IMPRESSION, object.getParent());
        }

        core.updateUi("Accessing " + object.getClass().getName() + " of Bed " + patient.getBedNo());
    }
}
