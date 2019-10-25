package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientDeleteCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return PatientDeleteSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        boolean deleted = false;
        DukeObject patient = core.uiContext.getObject();
        String searchCritical = getSwitchVal("critical");
        String searchInvestigation = getSwitchVal("investigation");
        String searchImpression = getSwitchVal("impression");

        if (searchCritical != null && searchCritical.equals(((Patient) patient).getPriDiagnosis().getName())) {
            ((Patient) patient).deletePriDiagnosis();
            deleted = true;
            System.out.println("Critical SEARCH");
            core.ui.print("Successfully deleted " + searchCritical);
        }
        if (searchInvestigation != null) {
            deleted = true;
            core.ui.print("Successfully deleted " + searchInvestigation);
        }
        if (searchImpression != null && ((Patient) patient).getImpressions().containsKey(searchImpression)) {
            ((Patient) patient).getImpressions().remove(searchImpression);
            deleted = true;
            core.ui.print("Successfully deleted " + searchImpression);
        }
        if (!deleted) {
            throw new DukeException("Unsuccessful delete, could not find a match.");
        }
    }
}

