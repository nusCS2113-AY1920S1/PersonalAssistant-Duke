package tests;

import duke.command.ArgCommand;
import duke.command.ObjCommand;
import duke.command.patient.PatientDeleteSpec;
import duke.command.patient.PatientEditSpec;
import duke.command.patient.PatientNewSpec;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.exception.DukeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PatientCommandTest extends CommandTest {

    private Patient patient;
    String[] impSwitchNames = {"impression"};
    String[] followSwitchNames = {"followup"};
    String[] critSwitchNames = {"critical"};
    String[] switchVals = {null};

    Impression imp;
    ObjCommand impCmd = null;
    ObjCommand followCmd = null;
    ObjCommand critCmd = null;
    /**
     * Sets up the core of this object to have a patient and impression, opens the impression, and stores those
     * references for comparison.
     */
    @BeforeEach
    public void setupPatient() {
        patient = new Patient("name", "bedNo", "allergies", 0, 0,
                0, 0, "", "");
        imp = new Impression("test", "test description", patient);
        try {
            core.patientData.addPatient(patient);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up patient! " + excp.getMessage());
        }
        core.uiContext.open(patient);
    }

    @AfterEach
    public void cleanup() {
        impCmd = null;
        followCmd = null;
        critCmd = null;
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
        try {
            Result testResult = new Result("test", imp, 1, "test result");
            Plan testPlan = new Plan("test", imp, 0, "0", "test plan");
            patient.addNewImpression(imp);
            imp.addNewTreatment(testPlan);
            imp.addNewEvidence(testResult);
            impCmd = new ObjCommand(PatientDeleteSpec.getSpec(), "1", impSwitchNames, switchVals);
            followCmd = new ObjCommand(PatientDeleteSpec.getSpec(), "test", followSwitchNames, switchVals);
            critCmd = new ObjCommand(PatientDeleteSpec.getSpec(), "1", critSwitchNames, switchVals);
            patient.update();
        } catch (DukeException excp) {
            fail("Exception thrown when setting up data: " + excp.getMessage());
        }

        try {
            // all deletions are unambiguous
            assertNotNull(imp.getTreatment("test"));
            assertNotNull(imp.getEvidence("test"));
            assertNotNull(patient.getImpression("test"));
            followCmd.execute(core);
            assertNull(imp.getTreatment("test"));
            critCmd.execute(core);
            assertNull(imp.getEvidence("test"));
            impCmd.execute(core);
            assertNull(patient.getImpression("test"));
        } catch (DukeException excp) {
            fail("Exception thrown when validly deleting objects: " + excp.getMessage());
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
