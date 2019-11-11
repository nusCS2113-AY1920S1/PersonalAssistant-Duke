package tests;

import duke.command.ArgCommand;
import duke.command.Command;
import duke.command.ObjCommand;
import duke.command.impression.ImpressionDeleteSpec;
import duke.command.impression.ImpressionEditSpec;
import duke.command.impression.ImpressionNewSpec;
import duke.command.impression.ImpressionPrimarySpec;
import duke.command.impression.ImpressionPrioritySpec;
import duke.command.impression.ImpressionResultSpec;
import duke.command.impression.ImpressionStatusSpec;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Patient;
import duke.data.Result;
import duke.exception.DukeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ImpressionCommandTest extends CommandTest {

    private Impression impression;
    private Patient patient;
    private Result result;
    private Medicine med;

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
            core.patientData.addPatient(patient);
            patient.addNewImpression(impression);
            result = new Result("result name", impression, 0, "Result summary");
            med = new Medicine("test medicine", impression, 2, "1", "test dose",
                    "today", "next two weeks");
            impression.addNewEvidence(result);
            impression.addNewTreatment(med);
        } catch (DukeException excp) {
            fail("Failed to setup patient and impression for testing!");
        }
        core.uiContext.open(impression);
    }

    @AfterEach
    public void cleanup() {
        patient = null;
        impression = null;
        med = null;
        result = null;
    }

    @Test
    public void impressionNewCommand_fullCommand_correctDataCreated() {
        String[] switchNames = {"medicine", "name", "priority", "status", "dose", "date", "duration"};
        String[] switchVals = {null, "test", "2", "1", "test dose", "today", "next two weeks"};

        try {
            Medicine testMed = new Medicine("test", impression, 2, "1", "test dose",
                    "today", "next two weeks");
            ArgCommand newMedCmd = new ArgCommand(ImpressionNewSpec.getSpec(), null, switchNames, switchVals);
            newMedCmd.execute(core);
            assertTrue(testMed.equals(impression.getTreatment("test")));
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid command: " + excp.getMessage());
        }
    }

    @Test
    public void impressionNewCommand_minCommand_correctDataCreated() {
        Medicine testMed = null;
        String[] switchNames = {"medicine", "name", "dose", "duration"};
        String[] switchVals = {null, "test", "test dose", "next two weeks"};

        try {
            testMed = new Medicine("test", impression, 0, "0", "test dose",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), "next two weeks");
        } catch (DukeException excp) {
            fail("Exception thrown when constructing valid Medicine!");
        }

        try {
            ArgCommand newMedCmd = new ArgCommand(ImpressionNewSpec.getSpec(), null, switchNames, switchVals);
            newMedCmd.execute(core);
            assertTrue(testMed.equals(impression.getTreatment("test")));
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid command: " + excp.getMessage());
        }
    }

    @Test
    public void impressionPrimaryCommand_fullCommand_setAsPrimary() {
        Command primaryCmd = new Command(ImpressionPrimarySpec.getSpec());
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

    // to test the search system, get the result object from the UiContext and inspect the list
    // assume user manages to identify an object unambiguously when testing ObjCommands

    @Test
    public void impressionEditCommand_editData_dataEdited() {
        ObjCommand editCmd = null;
        Result newData = null;
        String[] switchNames = {"evidence", "name", "priority", "summary"};
        String[] switchVals = {"1", "Edited", "1", "New result summary"};

        try {
            editCmd = new ObjCommand(ImpressionEditSpec.getSpec(), null, switchNames, switchVals);
            newData = new Result("Edited", impression, 1, "New result summary");
        } catch (DukeException excp) {
            fail("Error thrown while setting up Command and Result for editing!");
        }

        try {
            editCmd.execute(core, result); // assume origData was correctly identified by user
        } catch (DukeException excp) {
            fail("Error thrown while executing valid edit in Impression context!");
        }
        assertTrue(newData.equals(result));
    }

    @Test
    public void impressionEditCommand_editImpression_impressionEdited() {
        ObjCommand editCmd = null;
        Impression newImpression = null;
        String[] switchNames = {"impression", "name", "description"};
        String[] switchVals = {null, "new name", "new description"};

        try {
            editCmd = new ObjCommand(ImpressionEditSpec.getSpec(), null, switchNames, switchVals);
            newImpression = new Impression("new name", "new description", patient);
        } catch (DukeException excp) {
            fail("Exception thrown thrown while setting up Command and Result for editing: "
                    + excp.getMessage());
        }

        try {
            editCmd.execute(core); // functionality does not require any user input, use regular execute
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid edit in Impression context: "
                    + excp.getMessage());
        }
        assertTrue(newImpression.equals(impression));
    }

    @Test
    public void impressionDeleteCommand_validItem_itemDeleted() {
        ObjCommand delCmd = null;
        String[] switchNames = {"evidence"};
        String[] switchVals = {"1"};

        try {
            delCmd = new ObjCommand(ImpressionDeleteSpec.getSpec(), null, switchNames, switchVals);

        } catch (DukeException excp) {
            fail("Exception thrown thrown while setting up Command and Result for editing: "
                    + excp.getMessage());
        }

        try {
            delCmd.execute(core, result);
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid deletion in Impression context: "
                    + excp.getMessage());
        }
        assertNull(impression.getEvidence("result name"));
    }

    @Test
    public void impressionPriorityCommand_validItem_itemPrioritySet() {
        ObjCommand prioCmd = null;
        String[] switchNames = {"evidence", "set"};
        String[] switchVals = {"1", "1"};

        try {
            assertEquals(0, impression.getEvidenceAtIdx(0).getPriority());
            prioCmd = new ObjCommand(ImpressionPrioritySpec.getSpec(), null, switchNames, switchVals);
        } catch (DukeException excp) {
            fail("Exception thrown thrown while setting up Command and Result for editing: "
                    + excp.getMessage());
        }

        try {
            prioCmd.execute(core); // unambiguous
            assertEquals(1, impression.getEvidenceAtIdx(0).getPriority());
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid priority update in Impression context: "
                    + excp.getMessage());
        }
    }

    @Test
    public void impressionStatusCommand_validItem_itemStatusSet() {
        ObjCommand statusCmd = null;
        String[] switchNames = {"set"};
        String[] switchVals = {"completed"};

        try {
            assertEquals(1, impression.getTreatmentAtIdx(0).getStatusIdx());
            statusCmd = new ObjCommand(ImpressionStatusSpec.getSpec(), "1", switchNames, switchVals);
        } catch (DukeException excp) {
            fail("Exception thrown thrown while setting up Command and Result for editing: "
                    + excp.getMessage());
        }

        try {
            statusCmd.execute(core); // unambiguous
            assertEquals(2, impression.getTreatmentAtIdx(0).getStatusIdx());
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid status update in Impression context: "
                    + excp.getMessage());
        }
    }

    @Test
    public void impressionResultCommand_validInvx_resultCreated() {
        ObjCommand resultCmd = null;
        String[] switchNames = {"summary"};
        String[] switchVals = {"Uric acid was found to be extremely high."};
        Result invxResult = null;

        try {
            Investigation invx = new Investigation("Blood test", impression, 3, "1",
                    "Test patient's serum uric acid level.");
            invxResult = new Result("Blood test Result", impression, 3,
                    "Test patient's serum uric acid level.\n\nUric acid was found to be extremely high.");
            impression.addNewTreatment(invx);
            resultCmd = new ObjCommand(ImpressionResultSpec.getSpec(), "Blood test", switchNames, switchVals);
        } catch (DukeException excp) {
            fail("Exception thrown thrown while setting up Command and Result for editing: "
                    + excp.getMessage());
        }

        try {
            resultCmd.execute(core); // unambiguous
            assertTrue(invxResult.equals(impression.getEvidence("blood test result")));
        } catch (DukeException excp) {
            fail("Exception thrown while executing valid result command in Impression context: "
                    + excp.getMessage());
        }
    }
}
