package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;

public class HomeDischargeSpec extends HomeObjSpec {
    private static final HomeDischargeSpec spec = new HomeDischargeSpec();
    private static final String header = "DISCHARGED PATIENT REPORT";
    private static final String explanation = "This report shows all the data that was stored about a patient at the "
            + "time the report was created.";
    private static final String result = "Patient discharged. A discharge report have been created.";

    public static HomeDischargeSpec getSpec() {
        return spec;
    }

    private HomeDischargeSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum")
        );
    }

    @Override
    protected void executeWithObj(DukeCore core, DukeObject obj) throws DukeException {
        Patient patient = (Patient) obj;
        HomeReportSpec.createReport(patient, header, explanation, cmd.getSwitchVal("summary"));
        core.patientData.deletePatient(patient.getBedNo());
        core.writeJsonFile();
        core.updateUi(result);
    }
}
