package duke.command.home;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.ArgSpec;
import duke.command.Switch;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.exception.DukeFatalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HomeReportSpec extends ArgSpec {

    private static final HomeReportSpec spec = new HomeReportSpec();

    public static HomeReportSpec getSpec() {
        return spec;
    }

    private HomeReportSpec() {
        cmdArgLevel = ArgLevel.OPTIONAL;
        initSwitches(
                new Switch("bed", String.class, true, ArgLevel.REQUIRED, "b"),
                new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum")
        );
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
    super.execute(core);
        String header = "PATIENT REPORT";
        String explanation = "This report shows all the data that was stored about a patient at the time the report was"
                + " created.";

        if (core.patientList.getPatientByBed(cmd.getSwitchVal("bed")) != null) {
            createReport(core.patientList.getPatientByBed(cmd.getSwitchVal("bed")), header, explanation,
                    cmd.getSwitchVal("summary"));
            core.updateUi("Patient report created for "
                    + core.patientList.getPatientByBed(cmd.getSwitchVal("bed")).getName());
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
