package tests;

import duke.command.ArgCommand;
import duke.command.patient.PatientDeleteSpec;
import duke.command.patient.PatientEditSpec;
import duke.command.patient.PatientNewSpec;
import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
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
            core.patientData.addPatient(patient);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up patient! " + excp.getMessage());
        }
        core.uiContext.open(patient);
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

    @Test
    public void patientEditCommand_allSwitches_correctEditsMade() {
        String[] switchNames = {"name", "description"};
        String[] switchVals = {"test", "test description"};
        Impression imp = new Impression("test", "test description", patient);
        try {
            ArgCommand newImpCmd = new ArgCommand(PatientEditSpec.getSpec(), null, switchNames, switchVals);
            newImpCmd.execute(core);
            assertTrue(imp.equals(patient.getImpression("test")));
        } catch (DukeException excp) {
            fail("Exception thrown when validly creating patient from command!");
        }
    }

    @Test
    public void patientDeleteCommand_validTarget_correctObjectsDeleted() {
        String[] impSwitchNames = {"name", "description"};
        String[] impSwitchVals = {"test", "test description"};
        String[] treatSwitchNames = {"name", "description"};
        String[] treatSwitchNames = {"name", "description"};
        String[] evidSwitchVals = {"test", "test description"};
        String[] evidSwitchVals = {"test", "test description"};
        Impression imp = new Impression("test", "test description", patient);

        try {
            ArgCommand newImpCmd = new ArgCommand(PatientDeleteSpec.getSpec(), null, switchNames, switchVals);
            newImpCmd.execute(core);
            assertTrue(imp.equals(patient.getImpression("test")));
        } catch (DukeException excp) {
            fail("Exception thrown when validly creating patient from command!");
        }
    }

    @Test
    public void patientPrimaryCommand_validTarget_impressionSetAsPrimary() {
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
