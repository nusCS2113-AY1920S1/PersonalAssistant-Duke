package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientDeleteSpec extends ArgSpec {
    private static final PatientDeleteSpec spec = new PatientDeleteSpec();

    public static PatientDeleteSpec getSpec() {
        return spec;
    }

    private PatientDeleteSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("critical", String.class, true, ArgLevel.OPTIONAL, "c"),
                new Switch("investigation", String.class, true, ArgLevel.OPTIONAL, "in"),
                new Switch("impression", String.class, true, ArgLevel.OPTIONAL, "im"),
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        Patient patient = (Patient) core.uiContext.getObject();
        String searchCritical = cmd.getSwitchVal("critical");
        String searchInvestigation = cmd.getSwitchVal("investigation");
        String searchImpression = cmd.getSwitchVal("impression");

        if (searchCritical != null && (patient.getPrimaryDiagnosis()) != null) {
            if (patient.getPrimaryDiagnosis().getName().equals((searchCritical))) {
                patient.deletePriDiagnose();
                core.updateUi("Successfully deleted " + searchCritical);
            } else {
                core.updateUi("Unsuccessfully deleted patient's primary diagnosis does not match " + searchCritical);
            }
        } else if (searchCritical != null) {
            core.updateUi("Patient does not have a primary diagnosis.");
        }

        if (searchInvestigation != null) {
            // TODO
            core.updateUi("Not implemented yet, bug when adding treatments needs to be solved first");
            //core.ui.print("Successfully deleted " + searchInvestigation);
        }
        if (searchImpression != null) {
            if ((patient.getImpressionsObservableMap().containsKey(searchImpression))) {
                patient.deleteImpression(searchImpression);
                core.updateUi("Successfully deleted " + searchImpression);
            } else {
                core.updateUi("Unsuccessfully deleted, none of patient's impressions match " + searchImpression);
            }
        }

        patient.updateAttributes();
    }
}
