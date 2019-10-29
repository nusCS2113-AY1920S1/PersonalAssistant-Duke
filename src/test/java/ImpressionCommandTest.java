import duke.command.ArgCommand;
import duke.command.impression.ImpressionNewCommand;
import duke.data.Impression;
import duke.data.Medicine;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        patient.addNewImpression(impression);
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
            fail("Exception thrown while executing valid command!");
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
            fail("Exception thrown while executing valid command!");
        }
    }
}
