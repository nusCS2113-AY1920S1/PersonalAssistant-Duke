package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;

import java.util.ArrayList;

public class HomeFindCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return HomeFindSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String searchTerm = getArg();
        String findStr = "Here are the objects that contain '" + getArg() + "':\n";
        ArrayList<DukeObject> searchResult = new ArrayList<>();
        if (getSwitchVals().isEmpty()) {
            searchResult = core.patientMap.find(searchTerm);
        } else {
            ArrayList<Patient> filteredPatients = core.patientMap.findPatient(searchTerm);
            for (Patient patient : filteredPatients) {
                if (getSwitchVals().containsKey("patient")) {
                    searchResult.add(patient);
                }
                ArrayList<Impression> impressionResult = patient.findImpressions(searchTerm);
                for (Impression imp : impressionResult) {
                    if (getSwitchVals().containsKey("impression")) {
                        searchResult.add(imp);
                    }
                    if (getSwitchVals().containsKey("evidence")) {
                        searchResult.addAll(imp.findEvidences(searchTerm));
                    }
                    if (getSwitchVals().containsKey("treatment")) {
                        searchResult.addAll(imp.findTreatments(searchTerm));
                    }
                }
            }
        }

        StringBuilder information = new StringBuilder();

        for (int i = 0; i < searchResult.size(); i++) {
            information.append(i + 1).append(". ");
            DukeObject item = searchResult.get(i);
            if (item.getParent() != null) {
                if (item.getParent().getParent() != null) {
                    information.append(item.getParent().getParent().getName() + " - ");
                }
                information.append(item.getParent().getName() + " - ");
            }
            information.append(searchResult.get(i).getName()).append(System.lineSeparator());
        }
        core.ui.print(findStr + information.toString());
    }
}
