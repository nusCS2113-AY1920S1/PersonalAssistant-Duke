package tests;

import duke.command.ArgCommand;
import duke.command.patient.PatientNewCommand;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PatientCommandTest extends CommandTest {

    private Patient patient;

    /**
     * Sets up the core of this object to have a patient and impression, opens the impression, and stores those
     * references for comparison.
     */
    @BeforeEach
    public void setupPatient() {
        patient = new Patient("name", "bedNo", "allergies", 0, 0,
                0, 0, "", "");
        core.patientMap.addPatient(patient);
        core.uiContext.setContext(Context.PATIENT, patient);
    }

    @Test
    public void patientNewCommand_allSwitches_correctImpressionCreated() {
        String[] switchNames = {"name", "description"};
        String[] switchVals = {"test", "test description"};
        ArgCommand newImpCmd = new PatientNewCommand(null, switchNames, switchVals);
        Impression imp = new Impression("test", "test description", patient);
        try {
            newImpCmd.execute(core);
            assertTrue(imp.equals(patient.getImpression("test")));
        } catch (DukeException excp) {
            fail("Exception thrown when validly creating patient from command!");
        }
    }
}
