package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

public class HomeDischargeCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return HomeDischargeAndReportSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        String header = "DISCHARGED PATIENT REPORT";
        String explanation = "This report shows all the data that was stored about a patient at the time the report was"
                + " created.";
        if (core.patientMap.getPatient(getArg()) != null) {
            core.ui.print("Patient discharged. A discharge report have been created.");
            HomeReportCommand.createReport(core.patientMap.getPatient(getArg()), header,
                    explanation, getSwitchVal("summary"));
            core.patientMap.deletePatient(getArg());
        }
    }
}
