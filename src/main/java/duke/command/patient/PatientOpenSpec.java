package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.exception.DukeException;

import java.util.HashMap;
import java.util.Map;

public class PatientOpenSpec extends ObjSpec {
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
        super.execute(core);
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
                            + "INVESTIGATION) for the command!");
                }
            }
        }

        Patient patient = (Patient) core.uiContext.getObject();
        DukeObject object = PatientUtils.findFromPatient(core, type, cmd.getArg());
        if (object == null) {
            SearchResults results = PatientUtils.searchFromPatient(patient, type, cmd.getArg());
            processResults(core, results);
        } else {
            executeWithObj(core, object);
        }
   }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) {
        Patient patient = (Patient) core.uiContext.getObject();
        core.uiContext.open(obj);
        core.updateUi("Accessing " + obj.getClass().getSimpleName() + " of Bed " + patient.getBedNo());
    }
}
