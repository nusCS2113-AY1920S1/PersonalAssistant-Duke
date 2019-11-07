package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ObjSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResults;
import duke.exception.DukeException;

import java.util.ArrayList;

public class HomeFindSpec extends ObjSpec {

    private static final HomeFindSpec spec = new HomeFindSpec();

    public static HomeFindSpec getSpec() {
        return spec;
    }

    private HomeFindSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("patient", String.class, true, ArgLevel.NONE, "p"),
                new Switch("impression", String.class, true, ArgLevel.NONE, "i"),
                new Switch("evidence", String.class, true, ArgLevel.NONE, "e"),
                new Switch("treatment", String.class, true, ArgLevel.NONE, "t")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String searchTerm = cmd.getArg();
        SearchResults results = new SearchResults(searchTerm, new ArrayList<DukeObject>(), null);
        if (cmd.hasNoSwitches()) {
            results.addAll(core.patientData.searchAll(searchTerm));
        } else {
            if (cmd.isSwitchSet("patient")) {
                results.addAll(core.patientData.findPatients(searchTerm));
            }
            ArrayList<Patient> patientList = core.patientData.getPatientList();
            for (Patient patient : patientList) {
                if (cmd.isSwitchSet("impression")) {
                    results.addAll(patient.findImpressions(searchTerm));
                }
                ArrayList<Impression> impressionResult = patient.getImpressionList();
                for (Impression imp : impressionResult) {
                    if (cmd.isSwitchSet("evidence")) {
                        results.addAll(imp.findEvidences(searchTerm));
                    }
                    if (cmd.isSwitchSet("treatment")) {
                        results.addAll(imp.findTreatments(searchTerm));
                    }
                }
            }
        }
        core.search(results, cmd);
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) {
        core.uiContext.open(obj);
    }
}
