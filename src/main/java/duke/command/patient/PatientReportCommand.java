package duke.command.patient;

import duke.DukeCore;
import duke.command.Command;
import duke.command.home.HomeReportSpec;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientReportCommand extends Command {
    private static final String header = "DISCHARGED PATIENT REPORT";
    private static final String explanation = "This report shows all the data that was stored about a patient at the "
            + "time the report was created.";

    @Override
    public void execute(DukeCore core) throws DukeException {
        DukeObject patient = core.uiContext.getObject();
        HomeReportSpec.createReport((Patient) patient, header, explanation, null);
        core.updateUi("Patient report created for " + patient.getName());
    }
}
