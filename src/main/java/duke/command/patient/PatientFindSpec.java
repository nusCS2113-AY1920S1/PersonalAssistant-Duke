package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResult;
import duke.exception.DukeException;
import duke.ui.context.Context;

import java.util.ArrayList;

public class PatientFindSpec extends ArgSpec {

    private static final PatientFindSpec spec = new PatientFindSpec();

    public static PatientFindSpec getSpec() {
        return spec;
    }

    private PatientFindSpec() {
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(
                new Switch("impression", String.class, true, ArgLevel.NONE, "i"),
                new Switch("evidence", String.class, true, ArgLevel.NONE, "e"),
                new Switch("treatment", String.class, true, ArgLevel.NONE, "t")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        String searchTerm = cmd.getArg();
        Patient patient = (Patient) core.uiContext.getObject();
        ArrayList<DukeObject> resultList = new ArrayList<>();
        ArrayList<Impression> impressionResult;
        if (cmd.hasNoSwitches()) {
            resultList = patient.find(searchTerm);
        } else {
            impressionResult = patient.findImpressions(searchTerm);
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

        if (!resultList.isEmpty()) {
            SearchResult search = new SearchResult(searchTerm, resultList, patient);
            core.uiContext.setContext(Context.SEARCH, search);
            core.updateUi("Returning result of search of " + searchTerm);
        } else {
            throw new DukeException("No results found in this patient context.");
        }

        core.showSearchResults(searchTerm, resultList, patient);
    }
}
