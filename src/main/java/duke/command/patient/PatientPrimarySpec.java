package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.SearchResults;
import duke.exception.DukeException;

public class PatientPrimarySpec extends ObjSpec {
    private static final PatientPrimarySpec spec = new PatientPrimarySpec();

    public static PatientPrimarySpec getSpec() {
        return spec;
    }

    private PatientPrimarySpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches();
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        Impression impression = (Impression) PatientUtils.findFromPatient(core,"impression", cmd.getArg());
        if (impression == null) {
            SearchResults results = PatientUtils.getPatient(core).findImpressions(cmd.getArg());
            processResults(core, results);
        } else {
            executeWithObj(core, impression);
        }
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        assert (obj.getClass() == Impression.class);
        PatientUtils.getPatient(core).setPrimaryDiagnosis(obj.getName());
        core.writeJsonFile();
        core.updateUi("Primary diagnosis set!");
    }
}
