package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DischargeOrReportCommand extends ArgCommand {
    private String cmdStr;

    public DischargeOrReportCommand(String typeOfCommand) {
        super();
        cmdStr = typeOfCommand;
    }

    @Override
    protected ArgSpec getSpec() {
        return DischargeOrReportSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        if (core.patientMap.patientExist(getArg())) {
            String patientsName = core.patientMap.getPatient(getArg()).getName();
            String patientsBenNo = core.patientMap.getPatient(getArg()).getBedNo();
            try {
                FileWriter fileWriter = new FileWriter("data/reports" + File.separator + patientsName + "-"
                        + patientsBenNo + ".txt");
                if (cmdStr.equals("discharge")) {
                    fileWriter.write("DISCHARGED PATIENT REPORT\n\nThis report shows all the data that "
                            + "was stored about a patient at the time of discharge.\n\n");
                } else if (cmdStr.equals("report")) {
                    fileWriter.write("PATIENT REPORT\n\nThis report shows all the data that was stored about "
                            + "a patient at the time the report was created.\n\n");
                }
                if (getSwitchVal("summary") != null) {
                    fileWriter.write("Report Summary/Note: "
                            + getSwitchVal("summary") + ".\n\n");
                }
                fileWriter.write("Patient Data;\n");
                fileWriter.write(core.patientMap.getPatient(getArg()).toReportString());
                fileWriter.close();
            } catch (IOException e) {
                throw new DukeFatalException("Unable to create report! Some data may have been lost,");
            }
        }
        if ("discharge".equals(cmdStr)) {
            core.patientMap.deletePatient(getArg());
        } else if ("report".equals(cmdStr) && (core.patientMap.getPatient(getArg()) != null)) {
            core.ui.print("Report created");
        }
    }
}

