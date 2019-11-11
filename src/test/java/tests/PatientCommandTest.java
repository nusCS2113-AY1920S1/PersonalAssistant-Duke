package tests;

import duke.command.ArgCommand;
import duke.command.ObjCommand;
import duke.command.patient.PatientDeleteSpec;
import duke.command.patient.PatientEditSpec;
import duke.command.patient.PatientNewSpec;
import duke.command.patient.PatientPrimarySpec;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PatientCommandTest extends CommandTest {

    private Patient patient;
    private Impression imp;

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
        Patient newPatient = null;
        Patient newPatientPlus = null;
        ArgCommand editCmd = null;
        ArgCommand editCmdPlus = null;
        String[] switchNames = {"name", "bed", "allergies", "height", "weight", "age", "number",
            "address", "history"};
        String[] switchVals = {"new name", "new bed", "new allergies", "180", "100", "40", "95992020",
            "new address", "new history"};
        String[] switchNamesPlus = {"name", "bed", "allergies", "height", "weight", "age", "number",
            "address", "history", "append"};
        String[] switchValsPlus = {"plus", "plus", "plus", "177", "70", "20", "99111720", "plus", "plus", null};

        try {
            newPatient = new Patient("new name", "new bed", "new allergies", 180, 100,
                    40, 95992020, "new address", "new history");
            newPatientPlus = new Patient("new name plus", "new bed plus", "new allergies plus",
                    177, 70, 20, 99111720, "new address plus", "new history plus");
            editCmd = new ArgCommand(PatientEditSpec.getSpec(), null, switchNames, switchVals);
            editCmdPlus = new ArgCommand(PatientEditSpec.getSpec(), null, switchNamesPlus, switchValsPlus);
        } catch (DukeException excp) {
            fail("Exception thrown when setting up for editing: " + excp.getMessage());
        }

        try {
            editCmd.execute(core);
            assertTrue(newPatient.equals(patient));
            editCmdPlus.execute(core);
            assertTrue(newPatientPlus.equals(patient));
        } catch (DukeException excp) {
            fail("Exception thrown when editing: " + excp.getMessage());
        }
    }

    @Test
    public void patientDeleteCommand_validTarget_correctObjectsDeleted() {
        String[] impSwitchNames = {"impression"};
        String[] followSwitchNames = {"followup"};
        String[] critSwitchNames = {"critical"};
        String[] switchVals = {null};

        ObjCommand impCmd = null;
        ObjCommand followCmd = null;
        ObjCommand critCmd = null;
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
        String[] switchNames = {};
        String[] switchVals = {};
        Impression imp2 = new Impression("test2", "test description", patient);
        Impression imp3 = new Impression("test3", "test description", patient);
        ObjCommand priIdxCmd = null;
        ObjCommand priNameCmd = null;
        try {
            patient.addNewImpression(imp2);
            patient.addNewImpression(imp3);
            priIdxCmd = new ObjCommand(PatientPrimarySpec.getSpec(), "2", switchNames, switchVals);
            priNameCmd = new ObjCommand(PatientPrimarySpec.getSpec(), "test3", switchNames, switchVals);
        } catch (DukeException excp) {
            fail("Exception thrown when setting up patient and commands: " + excp.getMessage());
        }

        // get the impression at index 1, which in the UI is index 2
        Impression impIdx1 = patient.getImpressionList().get(1);

        try {
            // execution is unambiguous
            priIdxCmd.execute(core);
            assertEquals(impIdx1, patient.getPrimaryDiagnosis());
            priNameCmd.execute(core);
            assertEquals(imp3, patient.getPrimaryDiagnosis());
        } catch (DukeException excp) {
            fail("Exception thrown when setting up patient and commands: " + excp.getMessage());
        }
    }
}
