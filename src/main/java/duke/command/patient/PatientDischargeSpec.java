package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
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
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("index", Integer.class, true, ArgLevel.REQUIRED, "i"),
                new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        Patient patient = (Patient) core.uiContext.getObject();

        HomeReportSpec.createReport(patient, header, explanation, cmd.getSwitchVal("summary"));
        core.patientMap.deletePatient(patient.getBedNo());
        core.uiContext.setContext(Context.HOME, null);
        core.writeJsonFile();
        core.updateUi(result);
    }
}