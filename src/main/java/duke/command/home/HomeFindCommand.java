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
        ArrayList<DukeObject> resultList = new ArrayList<>();
        if (getSwitchVals().isEmpty()) {
            resultList = core.patientMap.find(searchTerm);
        } else {
            ArrayList<Patient> filteredPatients = core.patientMap.findPatient(searchTerm);
            for (Patient patient : filteredPatients) {
                if (getSwitchVals().containsKey("patient")) {
                    resultList.add(patient);
                }
                ArrayList<Impression> impressionResult = patient.findImpressions(searchTerm);
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
        }
        /*StringBuilder information = new StringBuilder();

        for (int i = 0; i < resultList.size(); i++) {
            information.append(i + 1).append(". ");
            DukeObject item = resultList.get(i);
            if (item.getParent() != null) {
                if (item.getParent().getParent() != null) {
                    information.append(item.getParent().getParent().getName()).append(" - ");
                }
                information.append(item.getParent().getName()).append(" - ");
            }
            information.append(resultList.get(i).getName()).append(System.lineSeparator());
        }*/
        core.showSearchResults(searchTerm, resultList, null);
    }
}
