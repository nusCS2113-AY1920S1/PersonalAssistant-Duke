package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientReportCommand extends Command {

    @Override
    public void execute(DukeCore core) throws DukeException {
        DukeObject patient = core.uiContext.getObject();
        String header = "PATIENT REPORT";
        String explanation = "This report shows all the data that was stored about a patient at the time the report was"
                + " created.";
        HomeReportCommand.createReport((Patient) patient, header, explanation, null);
        core.ui.print("Patient report created for " + patient.getName());
    }
}


