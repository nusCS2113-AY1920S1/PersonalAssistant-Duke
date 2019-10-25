package duke.command;

import duke.DukeCore;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HomeReportCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return HomeDischargeAndReportSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        String header = "PATIENT REPORT";
        String explanation = "This report shows all the data that was stored about a patient at the time the report was "
                + "created.";

        if (core.patientMap.getPatient(getArg()) != null) {
            createReport(core.patientMap.getPatient(getArg()), header, explanation, getSwitchVal("summary"));
            core.ui.print("Patient report created for " + core.patientMap.getPatient(getArg()).getName());
        }
    }

    public static void createReport(Patient patient, String header, String explanation, String Summary)
            throws DukeFatalException {
        try {
            FileWriter fileWriter = new FileWriter("data/reports" + File.separator + patient.getName() + "-"
                    + patient.getBedNo() + ".txt");
            fileWriter.write(header + "\n\n" + explanation);
            if (Summary != null) {
                fileWriter.write("Report Summary: " + Summary + "\n\n");
            }
            fileWriter.write("Patient Data;\n");
            fileWriter.write(patient.toReportString());
            fileWriter.close();
        } catch (IOException e) {
            throw new DukeFatalException("Unable to create report! Some data may have been lost,");
        }
    }
}