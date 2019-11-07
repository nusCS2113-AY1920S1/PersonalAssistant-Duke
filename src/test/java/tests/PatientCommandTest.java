package tests;

import duke.command.ArgCommand;
import duke.command.patient.PatientNewSpec;
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
        try {
            core.patientList.addPatient(patient);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up patient! " + excp.getMessage());
        }
        core.uiContext.setContext(Context.PATIENT, patient);
    }

    @Test
    public void patientNewCommand_allSwitches_correctImpressionCreated() {
        String[] switchNames = {"name", "description"};
        String[] switchVals = {"test", "test description"};
        Impression imp = new Impression("test", "test description", patient);
        try {
            ArgCommand newImpCmd = new ArgCommand(PatientNewSpec.getSpec(), null, switchNames, switchVals);
            newImpCmd.execute(core);
            assertTrue(imp.equals(patient.getImpression("test")));
        } catch (DukeException excp) {
            fail("Exception thrown when validly creating patient from command!");
        }
    }
}
