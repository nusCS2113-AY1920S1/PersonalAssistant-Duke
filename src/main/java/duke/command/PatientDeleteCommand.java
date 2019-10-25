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
        if (searchCritical != null) {
            comparePriDiagnosisName(core, searchCritical);
            deleted = true;
        }
        if (searchInvestigation != null) {
            compareInvestigationName(core, searchInvestigation);
            deleted = true;
        }
        if (searchImpression != null) {
            compareImpressionName(core, searchImpression);
            deleted = true;
        }
        if (!deleted) {
            throw new DukeException("Unsuccessful delete, could not find any matches");
        }
    }

    public void comparePriDiagnosisName(DukeCore core, String name) {
        if (((Patient) patient).containsPriDiagnosis(name)) {
            ((Patient) patient).deletePriDiagnosis();
            core.ui.print("Successfully deleted " + name);
        }
    }

    public void compareInvestigationName(DukeCore core, String name) {
        core.ui.print("Successfully deleted " + name);
    }

    public void compareImpressionName(DukeCore core, String name) throws DukeException {
        if (((Patient) patient).containsImpressionName(name)) {
            ((Patient) patient).deleteImpression2(name);
            core.ui.print("Successfully deleted " + name);
        }
    }
}