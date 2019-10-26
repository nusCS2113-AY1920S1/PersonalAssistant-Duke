package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientDeleteCommand extends ArgCommand {
    private DukeObject patient;

    @Override
    protected ArgSpec getSpec() {
        return PatientDeleteSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        patient = core.uiContext.getObject();
        String searchCritical = getSwitchVal("critical");
        String searchInvestigation = getSwitchVal("investigation");
        String searchImpression = getSwitchVal("impression");

        if (searchCritical != null && (((Patient) patient).getPriDiagnosis()) != null) {
            if (((Patient) patient).getPriDiagnosis().getName().equals((searchCritical))) {
                ((Patient) patient).deletePriDiagnose();
                core.ui.print("Successfully deleted " + searchCritical);
            } else {
                core.ui.print("Unsuccessfully deleted patient's primary diagnosis does not match " + searchCritical);
            }
        } else if (searchCritical != null && (((Patient) patient).getPriDiagnosis()) == null) {
            core.ui.print("Patient does not have a primary diagnosis.");
        }

        if (searchInvestigation != null) {
            // TODO
            core.ui.print("Not implemented yet, bug when adding investigations");
            //core.ui.print("Successfully deleted " + searchInvestigation);
        }
        if (searchImpression != null) {
            if ((((Patient) patient).getImpressionsObservableMap().containsKey(searchImpression))) {
                ((Patient) patient).deleteImpression(searchImpression);
                core.ui.print("Successfully deleted " + searchImpression);
            } else {
                core.ui.print("Unsuccessfully deleted, none of patient's impressions match " + searchImpression);
            }
        }
    }
}
