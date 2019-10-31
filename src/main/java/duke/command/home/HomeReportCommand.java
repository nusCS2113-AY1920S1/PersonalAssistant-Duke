package duke.command.home;

import duke.DukeCore;
import duke.command.ArgCommand;
import duke.command.ArgSpec;
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
        String explanation = "This report shows all the data that was stored about a patient at the time the report was"
                + " created.";

        if (core.patientMap.getPatient(getSwitchVal("bed")) != null) {
            createReport(core.patientMap.getPatient(getSwitchVal("bed")), header, explanation,
                    getSwitchVal("summary"));
            core.updateUi("Patient report created for "
                    + core.patientMap.getPatient(getSwitchVal("bed")).getName());
        }
    }

    /**
     * Creates a report file for a patient.
     * @param patient The patient that the report will be created for.
     * @param header The header of the report.
     * @param explanation An explanation that describes what the report will contain.
     * @param summary A summary that the doctor can write for the report, for example why the report is written.
     */
    public static void createReport(Patient patient, String header, String explanation, String summary)
            throws DukeFatalException {
        try {
            FileWriter fileWriter = new FileWriter("data/reports" + File.separator + patient.getName() + "-"
                    + patient.getBedNo() + ".txt");
            fileWriter.write(header + "\n\n" + explanation + "\n\n");
            if (summary != null) {
                fileWriter.write("Report Summary: " + summary + "\n\n");
            }
            fileWriter.write("Patient Data;\n");
            fileWriter.write(patient.toReportString());
            fileWriter.close();
        } catch (IOException e) {
            throw new DukeFatalException("Unable to create report! Some data may have been lost,");
        }
    }
}