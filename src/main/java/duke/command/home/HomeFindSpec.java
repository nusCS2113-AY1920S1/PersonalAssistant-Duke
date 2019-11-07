package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

import java.util.ArrayList;

public class HomeFindSpec extends ArgSpec {

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
        ArrayList<DukeObject> resultList = new ArrayList<>();
        if (cmd.hasNoSwitches()) {
            resultList = core.patientList.searchAll(searchTerm);
        } else {
            ArrayList<Patient> filteredPatients = core.patientList.findPatients(searchTerm);
            for (Patient patient : filteredPatients) {
                if (cmd.isSwitchSet("patient")) {
                    resultList.add(patient);
                }
                ArrayList<Impression> impressionResult = patient.findImpressions(searchTerm);
                for (Impression imp : impressionResult) {
                    if (cmd.isSwitchSet("impression")) {
                        resultList.add(imp);
                    }
                    if (cmd.isSwitchSet("evidence")) {
                        resultList.addAll(imp.findEvidences(searchTerm));
                    }
                    if (cmd.isSwitchSet("treatment")) {
                        resultList.addAll(imp.findTreatments(searchTerm));
                    }
                }
            }
        }

        core.showSearchResults(searchTerm, resultList, null);
    }
}
