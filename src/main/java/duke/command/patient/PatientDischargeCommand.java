package duke.command.patient;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
import duke.command.home.HomeReportCommand;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.Context;

public class PatientDischargeCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return PatientDischargeSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        DukeObject patient = core.uiContext.getObject();
        String header = "DISCHARGED PATIENT REPORT";
        String explanation = "This report shows all the data that was stored about a patient at the time the report was"
                + " created.";
        HomeReportCommand.createReport((Patient) patient, header,
                explanation, getSwitchVal("summary"));
        core.patientMap.deletePatient(((Patient) patient).getBedNo());
        core.uiContext.setContext(Context.HOME, null);
    }
}


