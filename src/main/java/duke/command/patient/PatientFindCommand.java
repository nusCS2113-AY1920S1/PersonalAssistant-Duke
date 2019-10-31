package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.SearchResult;
import duke.exception.DukeException;
import duke.ui.context.Context;

import java.util.ArrayList;

public class PatientFindCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return PatientFindSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String searchTerm = getArg();
        String findStr = "Here are the objects that contain '" + getArg() + "':\n";
        Patient patient = (Patient) core.uiContext.getObject();
        ArrayList<DukeObject> resultList = new ArrayList<>();
        ArrayList<Impression> impressionResult;
        if (getSwitchVals().isEmpty()) {
            resultList = patient.find(searchTerm);
        } else {
            impressionResult = patient.findImpressions(searchTerm);
            for (Impression imp : impressionResult) {
                if (getSwitchVals().containsKey("impression")) {
                    resultList.add(imp);
                }
                if (getSwitchVals().containsKey("evidence")) {
                    resultList.addAll(imp.findEvidences(searchTerm));
                }
                if (getSwitchVals().containsKey("treatment")) {
                    resultList.addAll(imp.findTreatments(searchTerm));
                }
            }
        }

        /*String information = "";

        for (int i = 0; i < resultList.size(); i++) {
            information += (i + 1) + ". " + resultList.get(i).getName() + "\n";
        }*/
        if (!resultList.isEmpty()) {
            SearchResult search = new SearchResult(searchTerm, resultList, patient);
            core.uiContext.setContext(Context.SEARCH, search);
            core.ui.print("Returning result of search of " + searchTerm);
        } else {
            throw new DukeException("No results found in this patient context.");
        }

        //core.ui.print(findStr + information);
        core.showSearchResults(searchTerm, resultList, patient);
    }
}
