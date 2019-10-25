package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

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
        ArrayList<DukeObject> searchResult = new ArrayList<>();
        ArrayList<Impression> impressionResult = new ArrayList<>();
        if (getSwitchVals().isEmpty()) {
            searchResult = patient.find(searchTerm);
        } else {
            impressionResult = patient.findImpression(searchTerm);
            for (Impression imp : impressionResult) {
                if (getSwitchVals().containsKey("impression")) {
                    searchResult.add(imp);
                }
                if (getSwitchVals().containsKey("evidence")) {
                    searchResult.addAll(imp.findEvidence(searchTerm));
                }
                if (getSwitchVals().containsKey("treatment")) {
                    searchResult.addAll(imp.findEvidence(searchTerm));
                }
            }
        }

        String information = "";

        for (int i = 0; i < searchResult.size(); i++) {
            information += (i + 1) + ". " + searchResult.get(i).getName() + "\n";
        }
        core.ui.print(findStr + information);
    }
}
