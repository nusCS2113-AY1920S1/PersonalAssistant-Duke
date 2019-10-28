package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.command.CommandHelpers;
import duke.data.Patient;
import duke.exception.DukeException;

public class HomeDischargeCommand extends ArgCommand {
    private static final String header = "DISCHARGED PATIENT REPORT";
    private static final String explanation = "This report shows all the data that was stored about a patient at the "
            + "time the report was created.";
    private static final String result = "Patient discharged. A discharge report have been created.";

    @Override
    protected ArgSpec getSpec() {
        return HomeDischargeAndReportSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        String bed = getSwitchVal("bed");
        int index = switchToInt("index");
        Patient patient = CommandHelpers.findPatient(core, bed, index);

        HomeReportCommand.createReport(patient, header, explanation, getSwitchVal("summary"));
        core.patientMap.deletePatient(patient.getBedNo());
        core.ui.print(result);
    }
}
