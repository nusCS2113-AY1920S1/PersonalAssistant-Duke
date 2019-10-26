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
        boolean deleted = false;
        String searchCritical = getSwitchVal("critical");
        String searchInvestigation = getSwitchVal("investigation");
        String searchImpression = getSwitchVal("impression");
        System.out.println(searchCritical + searchInvestigation + searchImpression);
        System.out.println(((Patient) patient).getPriDiagnosis());

        if (searchCritical != null && (((Patient) patient).getPriDiagnosis()) != null ){
            if (((Patient) patient).containsPriDiagnosis(searchCritical)) {
                ((Patient) patient).deletePriDiagnosis();
                //core.uiContext.setContext(Context.PATIENT, patient);
                core.ui.print("Successfully deleted " + searchCritical);
                deleted = true;
            }
        }

        if (searchInvestigation != null ) {
            // TODO
            core.ui.print("Successfully deleted " + searchInvestigation);
            deleted = true;
        }
        if (searchImpression != null) {
            if (((Patient) patient).containsImpressionName(searchImpression)) {
                ((Patient) patient).deleteImpression2(searchImpression);
                core.ui.print("Successfully deleted " + searchImpression);
            }
        }
        if (!deleted) {
            throw new DukeException("Unsuccessful delete, could not find any matches");
        }
    }
}


