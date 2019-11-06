package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgSpec;
import duke.command.home.HomeReportSpec;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;

public class PatientReportSpec extends ArgSpec {
    private static final String header = "DISCHARGED PATIENT REPORT";
    private static final String explanation = "This report shows all the data that was stored about a patient at the "
            + "time the report was created.";


    private static final PatientReportSpec spec = new PatientReportSpec();

    public static PatientReportSpec getSpec() {
        return spec;
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        DukeObject patient = core.uiContext.getObject();
        HomeReportSpec.createReport((Patient) patient, header, explanation, null);
        core.updateUi("Patient report created for " + patient.getName());
    }
}
