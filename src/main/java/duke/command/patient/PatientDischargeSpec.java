package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.home.HomeReportSpec;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;

public class PatientDischargeSpec extends ArgSpec {
    private static final PatientDischargeSpec spec = new PatientDischargeSpec();
    private static final String header = "DISCHARGED PATIENT REPORT";
    private static final String explanation = "This report shows all the data that was stored about a patient at the "
            + "time the report was created.";
    private static final String result = "Patient discharged. A discharge report have been created.";

    public static PatientDischargeSpec getSpec() {
        return spec;
    }

    private PatientDischargeSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches();
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String arg = (cmd.getArg() == null) ? "" : cmd.getArg();
        Patient patient = (Patient) core.uiContext.getObject();
        HomeReportSpec.createReport(patient, header, explanation, arg);
        core.patientData.deletePatient(patient.getBedNo());
        core.uiContext.setContext(Context.HOME, null);
        core.writeJsonFile();
        core.updateUi(result);
    }
}