package tests;

import templates.CommandTest;
import duke.command.ArgCommand;
import duke.command.Command;
import duke.command.impression.ImpressionNewCommand;
import duke.command.impression.ImpressionPrimaryCommand;
import duke.data.Impression;
import duke.data.Medicine;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ImpressionCommandTest extends CommandTest {

    private Impression impression;
    private Patient patient;

    /**
     * Sets up the core of this object to have a patient and impression, opens the impression, and stores those
     * references for comparison.
     */
    @BeforeEach
    public void setupPatientAndImpression() {
        patient = new Patient("name", "bedNo", "allergies", 0, 0,
                0, 0, "", "");
        impression = new Impression("name", "description", patient);
        try {
            patient.addNewImpression(impression);
        } catch (DukeException excp) {
            fail("Duplicate impression in patient, somehow.");
        }
        core.patientMap.addPatient(patient);
        core.uiContext.setContext(Context.IMPRESSION, impression);
    }

    @Test
    public void impressionNewCommand_fullCommand_correctDataCreated() {
        //TODO test other DukeData
        ArgCommand newMedCmd = new ImpressionNewCommand();
        String[] switchNames = {"medicine", "name", "priority", "status", "dose", "date", "duration"};
        String[] switchVals = {null, "test", "2", "1", "test dose", "today", "next two weeks"};
        setupCommand(newMedCmd, null, switchNames, switchVals);
        Medicine testMed = new Medicine("test", impression, 2, 1, "test dose",
                "today", "next two weeks");

        try {
            newMedCmd.execute(core);
            assertTrue(testMed.equals(impression.getTreatment("test")));
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid command: " + excp.getMessage());
        }
    }

    @Test
    public void impressionNewCommand_minCommand_correctDataCreated() {
        //TODO test other DukeData
        ArgCommand newMedCmd = new ImpressionNewCommand();
        String[] switchNames = {"medicine", "name", "dose", "duration"};
        String[] switchVals = {null, "test", "test dose", "next two weeks"};
        setupCommand(newMedCmd, null, switchNames, switchVals);
        Medicine testMed = new Medicine("test", impression, 0, 0, "test dose",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), "next two weeks");

        try {
            newMedCmd.execute(core);
            assertTrue(testMed.equals(impression.getTreatment("test")));
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid command: " + excp.getMessage());
        }
    }

    @Test
    public void impressionPrimaryCommand_fullCommand_setAsPrimary() {
        Command primaryCmd = new ImpressionPrimaryCommand();
        Impression newImpression = new Impression("name2", "description2", patient);
        try {
            patient.addNewImpression(newImpression);
        } catch (DukeException excp) {
            fail("Duplicate impression in patient, somehow.");
        }

        try {
            patient.setPrimaryDiagnosis("name2");
        } catch (DukeException excp) {
            fail("Exception thrown while setting primary diagnosis through API: " + excp.getMessage());
        }

        try {
            primaryCmd.execute(core);
        } catch (DukeException excp) {
            fail("Exception thrown while setting primary diagnosis through command: " + excp.getMessage());
        }
        assertEquals(impression, patient.getPrimaryDiagnosis());
    }
}
