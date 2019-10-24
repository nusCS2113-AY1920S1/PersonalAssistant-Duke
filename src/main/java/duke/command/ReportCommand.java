package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ReportSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        if (core.patientMap.patientExist(getArg())) {
            String patientsName = core.patientMap.getPatient(getArg()).getName();
            String patientsBenNo = core.patientMap.getPatient(getArg()).getBedNo();
            try {
                FileWriter fileWriter = new FileWriter("reports" + File.separator + patientsName + "-"
                        + patientsBenNo + ".txt");
                fileWriter.write("DISCHARGED PATIENT REPORT\n\nThis report shows all the data that was stored about "
                        + "a patient at the time of discharge.\n\nPatient Data;\n");
                if (getSwitchVal("summary") != null) {
                    fileWriter.write("Report Summary/Note: "
                            + getSwitchVal("summary") + ".\n\n");
                }
                fileWriter.write(core.patientMap.getPatient(getArg()).toReportString());
                fileWriter.close();
            } catch (IOException e) {
                throw new DukeFatalException("Unable to create report! Some data may have been lost,");
            }
        }
        core.patientMap.deletePatient(getArg());
    }
}
