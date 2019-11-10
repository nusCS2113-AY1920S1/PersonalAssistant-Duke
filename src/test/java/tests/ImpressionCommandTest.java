package tests;

import duke.command.ArgCommand;
import duke.command.Command;
import duke.command.ObjCommand;
import duke.command.impression.ImpressionEditSpec;
import duke.command.impression.ImpressionMoveSpec;
import duke.command.impression.ImpressionNewSpec;
import duke.command.impression.ImpressionPrimarySpec;
import duke.data.Impression;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.Result;
import duke.exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import templates.CommandTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
            core.patientData.addPatient(patient);
        } catch (DukeException excp) {
            fail("Failed to setup patient and impression for testing!");
        }
        core.uiContext.open(impression);
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
        Result origData = null;
        Result newData = null;
        String[] switchNames = {"evidence", "name", "priority", "summary"};
        String[] switchVals = {"1", "Edited", "1", "New result summary"};

        try {
            editCmd = new ObjCommand(ImpressionEditSpec.getSpec(), null, switchNames, switchVals);
            origData = new Result("Original", impression, 0, "Result summary");
            newData = new Result("Edited", impression, 1, "New result summary");
        } catch (DukeException excp) {
            fail("Error thrown while setting up Command and Result for editing!");
        }

        try {
            editCmd.execute(core, origData); // assume origData was correctly identified by user
        } catch (DukeException excp) {
            fail("Error thrown while executing valid edit in Impression context!");
        }
        assertTrue(newData.equals(origData));
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
    public void impressionMoveCommand_fullCommand_moveData() {
        ObjCommand moveCmd = null;
        Observation obsv = null;
        Impression newImpression = null;
        String[] switchNames = {"impression", "evidence"};
        String[] switchVals = {"name2", "name"};

        try {
            moveCmd = new ObjCommand(ImpressionMoveSpec.getSpec(), null, switchNames, switchVals);
            obsv = new Observation("name", impression, 0, "summary", true);
            impression.addNewEvidence(obsv);
            // add another observation to make identification ambiguous
            impression.addNewEvidence(new Observation("name2", impression, 0,
                    "summary", true));
            newImpression = new Impression("name2", "description2", patient);
            patient.addNewImpression(newImpression);
        } catch (DukeException excp) {
            fail("Exception thrown while setting up Command, Impression and Observation for move command test: "
                    + excp.getMessage());
        }

        try {
            moveCmd.execute(core, newImpression);
            core.uiContext.open(impression);
            moveCmd.execute(core, obsv);
        } catch (DukeException excp) {
            fail("Exception thrown while moving data: " + excp.getMessage());
        }
        assertNotNull(newImpression.getEvidence("name"));
    }

}
