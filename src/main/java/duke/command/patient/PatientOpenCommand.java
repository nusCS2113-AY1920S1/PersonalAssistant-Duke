package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.command.CommandUtils;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

import java.util.HashMap;
import java.util.Map;

public class PatientOpenCommand extends ArgCommand {
    @Override
    protected ArgSpec getSpec() {
        return PatientOpenSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        Map<String, Boolean> conditions = new HashMap<>();
        conditions.put("impression", isSwitchSet("impression"));
        conditions.put("critical", isSwitchSet("critical"));
        conditions.put("investigation", isSwitchSet("investigation"));

        String type = null;
        for (Map.Entry<String, Boolean> condition : conditions.entrySet()) {
            if (condition.getValue()) {
                if (type == null) {
                    type = condition.getKey();
                } else {
                    throw new DukeException("Please provide only 1 unique type (IMPRESSION, CRITICAL or INVESTIGATION "
                            + "that you wish to open!");
                }
            }
        }

        if (type == null) {
            throw new DukeException("You must provide 1 unique type (IMPRESSION, CRITICAL or INVESTIGATION) that you "
                    + "wish to open!");
        }

        Patient patient = (Patient) core.uiContext.getObject();
        DukeObject object = CommandUtils.findObject(core, patient, type, getSwitchVal("name"),
                switchToInt("index"));

        if ("impression".equals(type)) {
            core.uiContext.setContext(Context.IMPRESSION, object);
        } else if ("critical".equals(type)) {
            // TODO: Get critical
            core.uiContext.setContext(Context.IMPRESSION, object.getParent());
        } else {
            // TODO: Get investigation
            core.uiContext.setContext(Context.IMPRESSION, object.getParent());
        }

        core.ui.print("Accessing " + type + " of Bed " + patient.getBedNo());
    }
}
