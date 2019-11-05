package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.command.home.HomeReportSpec;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class PatientDischargeCommand extends ArgCommand {
    private static final String header = "DISCHARGED PATIENT REPORT";
    private static final String explanation = "This report shows all the data that was stored about a patient at the "
            + "time the report was created.";
    private static final String result = "Patient discharged. A discharge report have been created.";

    @Override
    protected ArgSpec getSpec() {
        return PatientDischargeSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        Patient patient = (Patient) core.uiContext.getObject();

        HomeReportSpec.createReport(patient, header, explanation, getSwitchVal("summary"));
        core.patientMap.deletePatient(patient.getBedNo());
        core.uiContext.setContext(Context.HOME, null);
        core.writeJsonFile();
        core.updateUi(result);
    }
}


